/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author tomas
 */
public class Tape {

    private final String path;
    public int number_of_records_on_tape;
    public int number_of_series;
    public int dummy = 0;
    public int number_of_records_in_buffor;
    private byte[] buffor;
    private DataInputStream input;
    private FileOutputStream output;
    public boolean can_read = true;
    private boolean can_read_buffer = true;
    public int used_records = 0;
    public long offsett = 0;
    public int id = 0;
    static public int liczba_odczytow = 0;
    static public int liczba_zapisow = 0;
    public Tape(String path, int ident) {
        this.path = path;
        number_of_records_on_tape = 0;
        number_of_records_in_buffor = 0;
        used_records = 0;
        number_of_series = 0;
        dummy = 0;
        id = ident;
        buffor = new byte[2 * 8 * 10];//10 rekordow w buforze
    }

    public void skip_offset(long off) throws IOException {
        System.out.println("Skipuje o " + input.skip(off));
    }

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public void write_record(Record Data,int type_of_reading) throws IOException {
        if (Data.invalid == false) {
            byte[] bytes = toByteArray(Data.getX());
            //wpisz w buffor
            for (int i = 0; i < 8; i++) {
                buffor[i + 16 * number_of_records_in_buffor] = bytes[i];
            }
            bytes = toByteArray(Data.getY());
            for (int i = 8; i < 16; i++) {
                buffor[i + 16 * number_of_records_in_buffor] = bytes[i - 8];
            }
            number_of_records_in_buffor++;
            if (number_of_records_in_buffor == 10) {
                write_buffor(buffor,type_of_reading);
            }
        }

    }

    public Record read_record(int type_of_reading) throws IOException {
        if (used_records < number_of_records_in_buffor && can_read == true) {

            double x, y;
            byte[] bytes = new byte[8];
            for (int i = 0; i < 8; i++) {
                bytes[i] = buffor[2 * 8 * used_records + i];
            }
            x = toDouble(bytes);

            for (int i = 8; i < 16; i++) {
                bytes[i - 8] = buffor[2 * 8 * used_records + i];
            }
            y = toDouble(bytes);
            used_records++;

            return new Record(x, y);
        } else if (used_records == number_of_records_in_buffor && can_read_buffer == true) {
            read_buffor(type_of_reading);
            return read_record(type_of_reading);
        } else if (used_records == number_of_records_in_buffor && can_read_buffer == false) {
         //   System.out.println("Koniec odczytu z piku id " + this.id + " OFFSET " + this.offsett + " USED_RECORDS " + this.used_records);
            can_read = false;
            //Record temp = new Record(-1, -1);
            //temp.invalid = true;
            return null;
        } else {
          //  System.out.println(" used_records = " + used_records + "number_of_records" + number_of_records_in_buffor + " can_read " + can_read);
            //Record temp = new Record(-1, -1);
            //temp.invalid = true;
            return null;
        }
    }

    public void add_reads(int type_of_reading){
        if(type_of_reading == 1){//reading for algorythm
            liczba_odczytow++;
        }
    }
    
    public void add_writes(int type_of_reading){
        if(type_of_reading == 1){//reading for algorythm
            liczba_zapisow++;
        }
    }
    public void swap_to_read(long off, int type_of_reading) throws IOException {
        can_read = true;
        can_read_buffer = true;
        used_records = 0;
        offsett = off;
        input = new DataInputStream(new FileInputStream(this.path));
        input.skip(off);
        read_buffor(type_of_reading);
    }

    public void swap_to_write() throws IOException {
        output = new FileOutputStream(this.path);
        buffor = new byte[2 * 8 * 10];
        number_of_records_in_buffor = 0;
    }

    public void close_read() throws IOException {
        input.close();
       // System.out.println("ID " + this.id + " offset " + this.offsett + " used " + this.used_records);
        offsett = 0;
        used_records = 0;

    }

    public void close_write(int type_of_reading) throws IOException {
        byte[] temp_buffor = new byte[2 * 8 * number_of_records_in_buffor];
        // System.out.println("close_write");
        // System.out.println(number_of_records_in_buffor);
        for (int i = 0; i < 2 * 8 * number_of_records_in_buffor; i++) {
            temp_buffor[i] = buffor[i];
        }
        // System.out.println(temp_buffor.length);
        write_buffor(temp_buffor,type_of_reading);
        output.close();
    }

    private void write_buffor(byte[] buf,int type_of_reading) throws FileNotFoundException, IOException {
        FileOutputStream output2 = new FileOutputStream(this.path, true);
        add_writes(type_of_reading);
        output2.write(buf);
        buffor = new byte[2 * 8 * 10];
        number_of_records_in_buffor = 0;
    }

    private void read_buffor(int type_of_reading) throws FileNotFoundException, IOException {
        int numberRead = 0;
        int number_of_index = 10;
        buffor = new byte[160];
        numberRead = input.read(buffor);
        number_of_index = numberRead / 16;
        add_reads(type_of_reading);
        if (numberRead == -1) {
            //    System.out.println("end of tape");
            can_read_buffer = false;
            can_read = false;
            number_of_records_in_buffor = 0;
        } else if (number_of_index < 10) {
            //   System.out.println("not full read");
            //  System.out.println(number_of_index);
            
            can_read_buffer = false;
            can_read = true;
            number_of_records_in_buffor = number_of_index;
        } else {
            offsett += 160;
            //   System.out.println("Succes_read!");
            //   System.out.println(number_of_index);
            number_of_records_in_buffor = number_of_index;
        }
        used_records = 0;
    }
}
