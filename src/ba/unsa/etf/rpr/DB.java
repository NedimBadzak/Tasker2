package ba.unsa.etf.rpr;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class DB {
    private Firestore db;

    DB() {
        Firestore db = connectToDB().getService();
        // [END firestore_setup_client_create]
        // [END fs_initialize]
        this.db = db;
    }

    private FirestoreOptions connectToDB() {
        InputStream serviceAccount =
                null;
        try {
//            serviceAccount = new FileInputStream("resources/firebase.json");
            serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        FirestoreOptions firestoreOptions =
                null;
        try {
            firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                    .setProjectId("usage-timer")
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Firestore db = firestoreOptions.getService();
        // [END firestore_setup_client_create_with_project_id]
        // [END fs_initialize_project_id]
        this.db = db;
        return firestoreOptions;
        // [END firestore_setup_client_create_with_project_id]
        // [END fs_initialize_project_id]
    }

    void addDocument(Map<String, Object> mapa) {

            mapa.put("completed", false);
            ApiFuture<DocumentReference> result = db.collection("tasks").add(mapa);
            try {
                DocumentReference documentReference = result.get();
                System.out.println(documentReference.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

    }

    private List<Task> dajSve() {
        ApiFuture<QuerySnapshot> query = db.collection("tasks").get();
        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Task> taskList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            String tekst = document.getString("tekst");
            String grupa = document.getString("category");
            Boolean completed = (document.getBoolean("completed") != null) ? document.getBoolean("completed") : false;
            Integer id = Math.toIntExact(document.getLong("id"));
            taskList.add(new Task(tekst, grupa, completed, id.intValue()));
        }

        return taskList;
    }

    public void ispisiSve() {
        var taskList = dajSve();
        taskList.sort(Comparator.comparingInt(Task::getId));
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(taskList.get(i));
        }
    }

    public void ispisi(String arg) {
        var taskList = dajSve();
        var novaLista = taskList.stream().filter(t -> t.grupa != null && t.grupa.equals(arg)).collect(Collectors.toList());
        novaLista.sort(Comparator.comparingInt(Task::getId));
        System.out.println("--------------------------");
        System.out.println("Tasks for: @" + arg);
        for (int i = 0; i < novaLista.size(); i++) {
            System.out.println(novaLista.get(i));
        }
        System.out.println("--------------------------");
    }

    public void uradi(String arg, Boolean boolArg) {
        int id = Integer.parseInt(arg);
        ApiFuture<QuerySnapshot> query = db.collection("tasks").whereEqualTo("id", id).get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        ApiFuture<WriteResult> result = db.collection("tasks").document(documents.get(0).getId()).update("completed", boolArg);
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
