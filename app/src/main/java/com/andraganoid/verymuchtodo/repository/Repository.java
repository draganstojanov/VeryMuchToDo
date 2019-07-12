package com.andraganoid.verymuchtodo.repository;

import com.andraganoid.verymuchtodo.model.Document;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class Repository {
//    private FirebaseFirestore todo;
//    private static final Repository ourInstance = new Repository();
//
//    public static Repository getInstance() {
//        return ourInstance;
//    }
//
//    private Repository() {
//        todo = FirebaseFirestore.getInstance();
//    }
//
//    public void addDocument(Document document) {
//        todo.collection(document.getCollection())
//                .document(document.getDocumentName())
//                .set(document.getMap());
//    }
//
//    public void deleteDocument(Document document) {
//        todo.collection(document.getCollection())
//                .document(document.getDocumentName())
//                .delete();
//    }

//    public void registerFirebaseListeners(RepositoryInterface repoInterface) {
//
//        todo.collection(Document.COLLECTION_TODO_LISTS)
//                .addSnapshotListener(this,new EventListener<QuerySnapshot>(){
//
//                            @Override
//                             void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//
//                            }
//
//
//
////                    @Override
////                    public void onEvent(
////                            @Nullable QuerySnapshot queryDocumentSnapshots,
////                            @Nullable FirebaseFirestoreException e) {
////
////                    }
//                }
//                );
//
//
//
//    }
//
//
//
//
////
////            todo.collection(COLLECTION_TODOS).
////
////    addSnapshotListener(this,new EventListener<QuerySnapshot>() {
////        @Override
////        public void onEvent (@Nullable QuerySnapshot
////        queryDocumentSnapshots, @Nullable FirebaseFirestoreException e){
////            long lastList = prefs.getLong("lastList", 0L);
////            int newLists = 0;
////            todoList.clear();
////            for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
////                TodoList t = qs.toObject(TodoList.class);
////                if (currentList != null) {
////                    if (t.getTitle().equals(currentList.getTitle())) {
////                        currentList = t;
////                    }
////                }
////
////                todoList.add(t);
////                System.out.println("TODO: " + t.getLastEdit());
////                if (Long.parseLong(t.getTimestamp()) > lastList) {
////                    newLists++;
////                }
////
////            }
////            if (todoList.size() > 0) {
////                Collections.sort(todoList, new Comparator<TodoList>() {
////                    @Override
////                    public int compare(final TodoList object1, final TodoList object2) {
////                        return object2.getTimestamp().compareTo(object1.getTimestamp());
////                    }
////                });
////            }
////
////            if (newLists > 0) {
////                prefs.edit().putLong("lastList", System.currentTimeMillis()).apply();
////
////                if (!todoList.get(0).getLastEditId().equals(myself.getId())) {
////                    findViewById(R.id.main_lists).setBackgroundColor(getResources().getColor(R.color.colorAccent));
////                }
////
////            }
////
////            for (TodoList m : todoList) {
////                System.out.println("TODOLIST: " + m.getTitle());
////
////
////            }
////            goFragment();
////
////        }
////    });


}
