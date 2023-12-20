package pokemon;

import java.io.*;

interface Serializable {
    void serialize(Object obj, String filename) throws IOException;
    Object deserialize(String filename) throws IOException, ClassNotFoundException;
}
class Serialiser implements Serializable {

    // Méthode pour sérialiser l'objet et enregistrer dans un fichier
    @Override
    public void serialize(Object obj, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj);
            System.out.println("Objet sérialisé avec succès.");
        }
    }

    // Méthode pour désérialiser l'objet à partir d'un fichier
    @Override
    public Object deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = in.readObject();
            System.out.println("Objet désérialisé avec succès.");
            return obj;
        }
    }
}
