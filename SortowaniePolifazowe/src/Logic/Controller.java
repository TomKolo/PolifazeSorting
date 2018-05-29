/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.IOException;

/**
 *
 * @author tomas
 */
public class Controller {

    public Tape tape1;
    public Tape tape2;
    public Tape tape3;

    private String path1;
    private String path2;
    private String path3;

    public int base_tape = 3;
    public int empty_tape = 3;

    private boolean is_fibb_1 = false;
    private boolean is_fibb_2 = false;

    public long offset_base = 0;
    public int already_used = 0;

    public boolean konice_sortowania = false;

    public Controller() {
    }

    public void initialize(String path, int option) throws IOException {
        if (option == 1) {
            path1 = path + "a.txt";
            path2 = path + "b.txt";
            path3 = path + "c.txt";
            tape1 = new Tape(path1, 1);
            tape1.swap_to_write();
            tape2 = new Tape(path2, 2);
            tape2.swap_to_write();
            tape3 = new Tape(path3, 3);
            tape3.swap_to_write();
        } else {
            String path_tmp = path;
            Tape tmp = new Tape(path_tmp, 4);
            path1 = path + "a.txt";
            path2 = path + "b.txt";
            path3 = path + "c.txt";
            tape3 = new Tape(path3, 3);
            copy_tape(tmp,tape3);
            tape2 = new Tape(path2, 2);
            tape2.swap_to_write();
            tape1 = new Tape(path1, 1);
            tape1.swap_to_write();
        }
    }

    public void copy_tape(Tape tape1_1, Tape tape2_2) throws IOException{
        tape1_1.swap_to_read((long)0,0);
        tape2_2.swap_to_write();
        while(tape1_1.can_read){
            Record tmp = tape1_1.read_record(0);
            if(tmp!= null){
            tape2_2.write_record(tmp,0);
            }
        }
        tape1_1.close_read();
        tape2_2.close_write(0);
    }
    public String getText(int choice) throws IOException {
        String temporary = "";
        int enter = 0;
        int in_line_records = 4;
        Record temp_record;
        int iterator = 1;
        switch (choice) {
            case 1:
                if (tape1.id == base_tape) {
                    tape1.swap_to_read(0,0);
                    tape1.used_records = 0;
                } else if (tape1.id == empty_tape) {
                    return temporary;
                } else {
                    tape1.swap_to_read(offset_base,0);
                    tape1.used_records = already_used;
                }
                while (tape1.can_read == true) {
                    temp_record = tape1.read_record(0);
                    if (temp_record == null || iterator >= 50) {
                        return temporary;
                    }
                    temporary += iterator + " ";
                    iterator++;
                    temporary += temp_record.toString();
                    temporary += " " + (int) temp_record.Distance();
                    temporary += '\n';
                    if (enter == in_line_records) {
                        temporary += '\n';
                    }
                    enter++;
                    enter %= in_line_records;
                }
                tape1.close_read();
                break;
            case 2:
                if (tape2.id == base_tape) {
                    tape2.swap_to_read(0,0);
                    tape2.used_records = 0;
                } else if (tape2.id == empty_tape) {
                    return temporary;
                } else {
                    tape2.swap_to_read(offset_base,0);
                    tape2.used_records = already_used;
                }
                while (tape2.can_read == true) {
                    temp_record = tape2.read_record(0);
                    if (temp_record == null || iterator >= 50) {
                        return temporary;
                    }
                    temporary += iterator + " ";
                    iterator++;
                    temporary += temp_record.toString();
                    temporary += " " + (int) temp_record.Distance();
                    temporary += '\n';
                    if (enter == in_line_records) {
                        temporary += '\n';
                    }
                    enter++;
                    enter %= in_line_records;
                }
                tape2.close_read();
                break;
            case 3:
                if (tape3.id == base_tape) {
                    tape3.swap_to_read(0,0);
                    tape3.used_records = 0;
                } else if (tape3.id == empty_tape) {
                    return temporary;
                } else {
                    tape3.swap_to_read(offset_base,0);
                    tape3.used_records = already_used;
                }
                while (tape3.can_read == true) {
                    temp_record = tape3.read_record(0);
                    if (temp_record == null || iterator >= 50) {
                        return temporary;
                    }
                    temporary += iterator + " ";
                    iterator++;
                    temporary += temp_record.toString();
                    temporary += " " + (int) temp_record.Distance();
                    temporary += '\n';
                    if (enter == in_line_records) {
                        temporary += '\n';
                    }
                    enter++;
                    enter %= in_line_records;
                }
                tape3.close_read();
                break;
            default:
                return "internal error";
        }
        return temporary;
    }

    public boolean first_distribute() throws IOException {
        int size_of_tape1 = 0;
        int size_of_tape2 = 0;
        double last_tape1 = 1000;
        double last_tape2 = 1000;
        int next_fib = 1;
        int prev_fib = 0;
        int pick_tape = 1;
        Record temp;
        tape3.swap_to_read(0,1);
        tape1.swap_to_write();
        tape2.swap_to_write();
        boolean just_swap = false;
        while (tape3.can_read == true) {
            temp = tape3.read_record(1);

            if (temp != null) {
                if ((pick_tape == 1 && just_swap == false) || (pick_tape == 2 && temp.Distance() >= last_tape1 && just_swap == true) || (pick_tape == 1 && temp.Distance() < last_tape2 && just_swap == true)) {
                    // System.out.println("Wstawiam " + temp.Distance() + " w tape " + pick_tape + " z just_swap " + just_swap + "dystanse last dla tape1 " + last_tape1 + "  " + last_tape2 + "size of t1" + size_of_tape1 + "  " + size_of_tape2);
                    if (temp.Distance() >= last_tape1) {
                        last_tape1 = temp.Distance();
                        tape1.write_record(temp,1);
                    } else {
                        last_tape1 = temp.Distance();
                        tape1.write_record(temp,1);
                        size_of_tape1++;
                        is_fibb_1 = false;
                        just_swap = false;
                        if (size_of_tape1 == next_fib) {
                            is_fibb_1 = true;
                            int temp_fib = size_of_tape1;
                            next_fib = next_fib + prev_fib;
                            prev_fib = temp_fib;
                            pick_tape = 2;
                            just_swap = true;
                        }
                    }
                } else if ((pick_tape == 2 && just_swap == false) || (pick_tape == 1 && temp.Distance() >= last_tape2 && just_swap == true) || (pick_tape == 2 && temp.Distance() < last_tape1 && just_swap == true)) {
                    // System.out.println("Wstawiam " + temp.Distance() + " w tape " + pick_tape + " z just_swap " + just_swap + "dystanse last dla tape1 " + last_tape1 + "  " + last_tape2 + "size of t1" + size_of_tape1 + "  " + size_of_tape2);
                    if (temp.Distance() >= last_tape2) {
                        last_tape2 = temp.Distance();
                        tape2.write_record(temp,1);
                    } else {
                        last_tape2 = temp.Distance();
                        tape2.write_record(temp,1);
                        size_of_tape2++;
                        just_swap = false;
                        is_fibb_2 = false;
                        if (size_of_tape2 == next_fib) {
                            is_fibb_2 = true;
                            int temp_fib = size_of_tape2;
                            next_fib = next_fib + prev_fib;
                            prev_fib = temp_fib;
                            pick_tape = 1;
                            just_swap = true;
                        }
                    }
                } else {
                    // System.out.println("Nie wstawiam ??? dystans: " + temp.Distance() + " w tape " + pick_tape + " z just_swap " + just_swap + "dystanse last dla tape1 " + last_tape1 + "  " + last_tape2 + "size of t1" + size_of_tape1 + "  " + size_of_tape2);
                }
            }
        }
        tape1.close_write(1);
        tape2.close_write(1);
        tape3.close_read();
        empty_tape = 3;
        //dummy plus pick base and empty tape;
        if (is_fibb_1 == true && is_fibb_2 == true) {
            if (size_of_tape1 >= size_of_tape2) {
                base_tape = 1;
            } else {
                base_tape = 2;
            }
        } else if (is_fibb_1) {
            tape2.dummy = next_fib - size_of_tape2;
            base_tape = 2;
        } else if (is_fibb_2) {
            tape1.dummy = next_fib - size_of_tape1;
            base_tape = 1;
        } else {
            // System.out.println("internal error, not fibbonacii size of tepes");
        }
        System.out.println("TAPE INFO | TAPE1:" + size_of_tape1 + "Dummys :" + tape1.dummy + "is_fibb " + is_fibb_1 + "TAPE2:" + size_of_tape2 + "Dummy :" + tape2.dummy + "is_fibb" + is_fibb_2);
        return true;
    }

    public boolean sort() throws IOException {
        Tape base, tape_one = null, empty;
        boolean can_sort = true;
        boolean is_series = true;
        Record next_tape_one = null;
        Record next_base = null;
        Record temp_dummy = new Record(0.0, 0.0);
        Record last_base = new Record(0.0, 0.0);
        Record last_empty = new Record(0.0, 0.0);
        temp_dummy.invalid = true;
        base = pick_tape(base_tape);
        empty = pick_tape(empty_tape);
        for (int i = 1; i <= 3; i++) {
            if (base_tape != i && empty_tape != i) {
                tape_one = pick_tape(i);
            }
        }
        //System.out.println("Base Tape" + base.id + "Empty Tape" + empty.id + "Tape one:" + tape_one.id);
        base.swap_to_read(0,1);
        tape_one.swap_to_read(offset_base,1);
        tape_one.used_records = already_used;
        empty.swap_to_write();
        boolean first_iteration = true;
        //scalanie !!
        while (can_sort) //najpierw przepisz dummys
        {
            if (base.dummy > 0) {
                base.dummy--;
                is_series = true;
                temp_dummy = new Record(0.0, 0.0);
                temp_dummy.invalid = true;
                while (is_series) {
                    if (next_tape_one == null) {
                        next_tape_one = tape_one.read_record(1);
                    }

                    if (next_tape_one.Distance() >= temp_dummy.Distance() && next_tape_one.invalid == false) {
                        is_series = true;
                        temp_dummy = next_tape_one;
                        empty.write_record(next_tape_one,1);
                        System.out.println("wpisuje do empty z tape_one" + next_tape_one.Distance());
                        next_tape_one = null;
                        last_empty.invalid = true;
                    } else {
                        System.out.println(" nie wpisuje do empty z tape_one is is_series == false" + next_tape_one.Distance());
                        is_series = false;

                    }
                }
            } else {//sorotowanie bez dummych
                is_series = true;
                last_empty = new Record(0.0, 0.0);
                last_empty.invalid = true;
                
                while (is_series) {
                    if (next_base == null && base.can_read == true) {
                        next_base = base.read_record(1);
                    }
                    if (next_tape_one == null && tape_one.can_read == true) {
                        next_tape_one = tape_one.read_record(1);
                    }
                    if (next_base == null) {
                        if (next_tape_one == null) {
                            is_series = false;
                            can_sort = false;
                        } else if (next_tape_one.Distance() >= last_empty.Distance()) {
                            empty.write_record(next_tape_one,1);
                            last_empty = next_tape_one;
                            next_tape_one = null;
                        } else {
                            //tu nigdy nie pwoinien wejść
                            is_series = false;
                            can_sort = false;
                        }
                    } else if (next_tape_one == null) {
                        if (next_base.Distance() >= last_empty.Distance()) {
                            empty.write_record(next_base,1);
                            last_empty = next_base;
                            //System.out.println("Wpisuje z base kiedy tape_one to null : last_empty is null" + " naxt_base " + next_base.Distance() + " next_tape_one " + next_tape_one.Distance());
                            next_base = null;
                        } else {
                            is_series = false;
                            can_sort = false;

                        }
                    } else if (next_base.Distance() >= last_empty.Distance() || next_tape_one.Distance() >= last_empty.Distance()) { // tu łapie null prt
                        if ((next_base.Distance() <= next_tape_one.Distance() && next_base.Distance() >= last_empty.Distance()) || (next_base.Distance() >= last_empty.Distance() && next_tape_one.Distance() < last_empty.Distance())) {
                            empty.write_record(next_base,1);
                            last_empty = next_base;
                            //System.out.println("Wpisuje z base : last_empty:" + last_empty.Distance() + " naxt_base " + next_base.Distance() + " next_tape_one " + next_tape_one.Distance());
                            last_base = next_base;
                            next_base = null;
                        } else if ((next_base.Distance() >= next_tape_one.Distance() && next_tape_one.Distance() >= last_empty.Distance()) || (next_tape_one.Distance() >= last_empty.Distance() && next_base.Distance() < last_empty.Distance())) {
                            empty.write_record(next_tape_one,1);
                            last_empty = next_tape_one;
                            //System.out.println("Wpisuje z tape_one : last_empty:" + last_empty.Distance() + " naxt_base " + next_base.Distance() + " next_tape_one " + next_tape_one.Distance());
                            next_tape_one = null;
                        } else {
                            //System.out.println("last_empty:" + last_empty.Distance() + " naxt_base " + next_base.Distance() + " next_tape_one " + next_tape_one.Distance());
                            //System.out.println("crap");
                        }
                    } else {
                        is_series = false;
                        last_empty = new Record(0.0, 0.0);
                        last_empty.invalid = true;
                        //System.out.println(" is_series == false last_empty:" + last_empty.Distance() + " naxt_base " + next_base.Distance() + " next_tape_one " + next_tape_one.Distance());
                    }
                }

                if (next_base == null && base.can_read == true) {
                    next_base = base.read_record(1);
                }
                //warunek kończąy sortowanie
                //               System.out.println("warunek_kończący next_base " + next_base.Distance() + " prev_base " + last_base.Distance() + " tape_one.can_read" + tape_one.can_read + " BASE CAN READ " + base.can_read);
                if (tape_one.can_read == false && base.can_read == false) {
                    if (first_iteration == true) {
                        System.out.println("koniec sortoawania !");
                        konice_sortowania = true;
                    }
                    can_sort = false;
                    is_series = false;
                } else if (tape_one.can_read == false && next_base.Distance() < last_base.Distance()) {
                    can_sort = false;
                }
                first_iteration = false;
            }
        }
        offset_base = base.offsett;
        already_used = base.used_records;
        if (base.number_of_records_in_buffor == 10) {
            offset_base -= 160;
        }
        already_used -= 1;

        System.out.println("OFFSE T" + offset_base + " allrady_used" + already_used + "tape_id" + base.id);
        base_tape = empty.id;
        empty_tape = tape_one.id;
        base.close_read();
        tape_one.close_read();
        empty.close_write(1);
        return true;
    }

    private Tape pick_tape(int choice) {
        switch (choice) {
            case 1:
                return tape1;
            case 2:
                return tape2;
            case 3:
                return tape3;
            default:
                System.out.println("internal error, no tape picked");
                break;
        }
        return tape3;
    }
    
    public boolean check_tape(int number_of_tape) throws IOException{
        Tape tmp;
        switch (number_of_tape) {
            case 1:
                tmp = tape1;
                break;
            case 2:
                tmp = tape2;
                break;
            default:
                tmp=tape3;
                break;
        }
        
        tmp.swap_to_read(0,0);
        Record temporary;
        Record prev = new Record(0,0);
        while(tmp.can_read == true){
            temporary = tmp.read_record(0);
            if(temporary == null){
                return true;
            }
            if(prev.Distance() > temporary.Distance()){
                return false;
            }
            prev = temporary;
        }
        return true;
    }
}
