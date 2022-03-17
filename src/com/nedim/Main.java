package com.nedim;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        DB db = new DB();
        Map<String, Object> mapa = new HashMap<>();
        String task = "";
        if(args.length != 0) {
            if(args[0].equals("-get") && args.length == 1) {
                db.ispisiSve();
                return;
            } else if(args[0].equals("-get")) {
                db.ispisi(args[1]);
                return;
            } else if(args[0].equals("-check") && args.length>1) {
                db.uradi(args[1], true);
                return;
            } else if(args[0].equals("-uncheck") && args.length>1) {
                db.uradi(args[1], false);
                return;
            } else {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].contains("@")) {
                        if(mapa.containsKey("category")) mapa.put("category", mapa.get("category") + " " + args[i].substring(1));
                        else mapa.put("category", args[i].substring(1));
                        continue;
                    }
                    task = task + " " + args[i];
                }
            }
        } else {
            System.out.println("Unesite task: ");
            Scanner in = new Scanner(System.in);
            task = in.nextLine();
        }
        if(task != "") {
            List<String> lista = List.of(task.split(" "));
            String resultText = "";
            for (String s : lista) {
                if (s.contains("@")) {
                    if(mapa.containsKey("category")) mapa.put("category", mapa.get("category") + " " + s.substring(1));
                    else mapa.put("category", s.substring(1));
                } else resultText = resultText + s + " ";
            }
            mapa.put("tekst", resultText);
        }
        try {
            db.addDocument(mapa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
