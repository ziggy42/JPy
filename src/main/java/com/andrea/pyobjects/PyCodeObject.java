package com.andrea.pyobjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PyCodeObject {

    private int co_argcount;
    private int co_nlocals;
    private int co_stacksize;
    private int co_flags;
    private int co_kwonlyargcount;
    private String co_name;
    private String co_filename;
    private Integer[] co_code;
    private Object[] co_consts;
    private String[] co_names;
    private String[] co_varnames;
    private String[] co_freevars;
    private String[] co_cellvars;
    //private int co_firstlineno;
    //private String co_lnotab;

    public PyCodeObject(int co_argcount, int co_nlocals, int co_stacksize, int co_flags, int co_kwonlyargcount,
                        String co_name, String co_filename, Integer[] co_code, Object[] co_consts, String[] co_names,
                        String[] co_varnames, String[] co_freevars, String[] co_cellvars) {
        this.co_argcount = co_argcount;
        this.co_nlocals = co_nlocals;
        this.co_stacksize = co_stacksize;
        this.co_flags = co_flags;
        this.co_kwonlyargcount = co_kwonlyargcount;
        this.co_name = co_name;
        this.co_filename = co_filename;
        this.co_code = co_code;
        this.co_consts = co_consts;
        this.co_names = co_names;
        this.co_varnames = co_varnames;
        this.co_freevars = co_freevars;
        this.co_cellvars = co_cellvars;
    }

    public int getCo_argcount() {
        return co_argcount;
    }

    public int getCo_nlocals() {
        return co_nlocals;
    }

    public int getCo_stacksize() {
        return co_stacksize;
    }

    public int getCo_flags() {
        return co_flags;
    }

    public int getCo_kwonlyargcount() {
        return co_kwonlyargcount;
    }

    public String getCo_name() {
        return co_name;
    }

    public String getCo_filename() {
        return co_filename;
    }

    public Integer[] getCo_code() {
        return co_code;
    }

    public Object[] getCo_consts() {
        return co_consts;
    }

    public String[] getCo_names() {
        return co_names;
    }

    public String[] getCo_varnames() {
        return co_varnames;
    }

    public String[] getCo_freevars() {
        return co_freevars;
    }

    public String[] getCo_cellvars() {
        return co_cellvars;
    }

    public int getCodeLength() {
        return co_code.length;
    }

    public String getVarName(int index) {
        return co_varnames[index];
    }

    public Object getConst(int index) {
        return co_consts[index];
    }

    public String getName(int index) {
        return co_names[index];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("co_argcount: ").append(this.co_argcount)
                .append("\nco_nlocals: ").append(this.co_nlocals)
                .append("\nco_stacksize: ").append(this.co_stacksize)
                .append("\nco_flags: ").append(this.co_flags)
                .append("\nco_kwonlyargcount: ").append(this.co_kwonlyargcount)
                .append("\nco_name: ").append(this.co_name)
                .append("\nco_filename: ").append(this.co_filename)
                .append("\nco_code: ");

        for (Integer i : co_code)
            stringBuilder.append(i).append(" ");

        stringBuilder.append("\nco_consts: ");
        for (Object obj : co_consts)
            stringBuilder.append(obj).append(" ");

        stringBuilder.append("\nco_names: ");
        for (String str : co_names)
            stringBuilder.append(str).append(" ");

        stringBuilder.append("\nco_varnames: ");
        for (String str : co_varnames)
            stringBuilder.append(str).append(" ");

        stringBuilder.append("\nco_freevars: ");
        for (String str : co_freevars)
            stringBuilder.append(str).append(" ");

        stringBuilder.append("\nco_cellvars: ");
        for (String str : co_cellvars)
            stringBuilder.append(str).append(" ");

        return stringBuilder.toString();
    }

    public static PyCodeObject buildPyCodeObject(BufferedReader in) throws IOException {
        int co_argcount = Integer.parseInt(in.readLine());
        int co_nlocals = Integer.parseInt(in.readLine());
        int co_stacksize = Integer.parseInt(in.readLine());
        int co_flags = Integer.parseInt(in.readLine());

        List<String> mList = Arrays.asList(in.readLine().trim().split(" "));
        Integer[] co_code = mList.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList()).toArray(new Integer[mList.size()]);

        List<Object> consts = new ArrayList<>();
        int nConsts = Integer.parseInt(in.readLine());
        for (int i = 0; i < nConsts; i++) {
            String cons = in.readLine();
            if ("SOC".equals(cons))
                consts.add(PyCodeObject.buildPyCodeObject(in));
            else if (cons.startsWith("'") && cons.endsWith("'"))
                consts.add(cons.substring(1, cons.length() - 1));
            else if ("None".equals(cons))
                consts.add(null);
            else if (cons.contains("."))
                consts.add(Double.parseDouble(cons));
            else
                consts.add(Long.parseLong(cons));
        }
        Object[] co_consts = consts.toArray();
        String[] co_names = in.readLine().trim().split(" ");
        String[] co_varnames = in.readLine().trim().split(" ");
        String[] co_freevars = in.readLine().trim().split(" ");
        String[] co_cellvars = in.readLine().trim().split(" ");
        String co_filename = in.readLine().trim();
        String co_name = in.readLine().trim();
        int co_kwonlyargcount = Integer.parseInt(in.readLine());

        return new PyCodeObject(co_argcount, co_nlocals, co_stacksize, co_flags, co_kwonlyargcount, co_name, co_filename,
                co_code, co_consts, co_names, co_varnames, co_freevars, co_cellvars);
    }
}
