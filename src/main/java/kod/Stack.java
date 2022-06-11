package kod;

public class Stack {
    int[] tab;
     int firstFree;
 
     // Konstruktor, który zainicjuje wierzchołek i stos o odpowiedniej
     // wielkości.
     public Stack(int MaxSize) {
         tab = new int[MaxSize];
         firstFree = 0;
     }
 
     // Metoda zwracająca maksymalny rozmiar stosu
     int getMaximumStackSize() {
         return tab.length;
     }
 
     // Metoda zwracająca prawdę, jeżeli stos jest pusty
     boolean isEmpty() {
         if (firstFree == 0) {
             return true;
         } else
             return false;
     }
 
     // Metoda zwracająca rozmiar stosu
     int getSize() {
         return firstFree;
     }
 
     // Metoda dodająca na stos
     void Push(int E) throws ArrayIndexOutOfBoundsException {
         if (firstFree < tab.length) {
             tab[firstFree] = E;
             firstFree++;
 
         } else {
             throw new ArrayIndexOutOfBoundsException(
                     "Przepełnienie stosu, operacja nie powiodła się");
         }
     }
 
     // Metoda zdejmująca ze stosu
     int Pop() throws IndexOutOfBoundsException {
         if (firstFree <= 0) {
             throw new IndexOutOfBoundsException(
                     "Stos jest pusty, operacja nie powiodła się");
         }
 
         int temp = tab[firstFree - 1];
         firstFree--;
         return temp;
     }
 
     // Metoda wyświetlająca zawartość stosu
     void display() throws IndexOutOfBoundsException {
         if (firstFree == 0) {
             throw new IndexOutOfBoundsException(
                     "Stos jest pusty, operacja nie powiodła się");
         }
 
         int temp = firstFree - 1;
         do {
             System.out.println(tab[temp]);
             temp--;
         } while (temp > -1);
 
     }
 
     // Metoda wielokrotnego zdjęcia
     void multiPop(int k) {
         if (k < firstFree) {
             for (int i = k; i > 0; i--) {
                 System.out.println(Pop());
             }
         }
     }
 
     // Metoda czyszcząca stos
     void clear() {
         for (int i = 0; i < firstFree; i++) {
             tab[i] = 0;
 
         }
 
         firstFree = 0;
     }
 
     // Metoda odwracająca kolejność elementów na stosie
     void reverse() {
         for (int i = 0; i < firstFree / 2; i++) {
             int temp = tab[i];
             tab[i] = tab[firstFree - 1 - i];
             tab[firstFree - 1 - i] = temp;
 
         }
     }
 }
    
