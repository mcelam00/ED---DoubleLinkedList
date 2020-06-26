package ule.edi.doubleLinkedList;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.*;

import ule.edi.exceptions.EmptyCollectionException;

public class DoubleLinkedImplTest {
	DoubleLinkedListImpl<String> lv;
	DoubleLinkedListImpl<String> listaConElems;
	
@Before
public void antesDe() {
	lv=new DoubleLinkedListImpl<String>(); //se crea una lista vacia
	listaConElems=new DoubleLinkedListImpl<String>(); //se crea una lista nueva
	listaConElems.insertFirst("D"); //se aniaden elementos
	listaConElems.insertFirst("B");
	listaConElems.insertFirst("A");
	listaConElems.insertFirst("C");
	listaConElems.insertFirst("B");
	listaConElems.insertFirst("A");
	
}


	
	@Test
	public void isEmptyTest() {
		Assert.assertTrue(lv.isEmpty());
		Assert.assertTrue(lv.size()==0);
		Assert.assertFalse(listaConElems.isEmpty());
		Assert.assertTrue(listaConElems.size()==6);
		
	}
	
	@Test
	public void clearTest() {
		lv.clear();
		Assert.assertTrue(lv.isEmpty());
		Assert.assertTrue(lv.size()==0);
		
		listaConElems.clear();
		Assert.assertTrue(listaConElems.isEmpty());
		Assert.assertTrue(listaConElems.size()==0);
		Assert.assertEquals(listaConElems.toString(),listaConElems.toStringReverse());
		
	}
	
	@Test
	public void containsTest() {
		Assert.assertFalse(lv.contains("A"));
		Assert.assertTrue(listaConElems.contains("A"));
		Assert.assertTrue(listaConElems.contains("B"));
		Assert.assertTrue(listaConElems.contains("B"));
		Assert.assertTrue(listaConElems.contains("D"));
		Assert.assertFalse(listaConElems.contains("Z"));
		
	}
	
	@Test
	public void removeAllTest() throws EmptyCollectionException {
        Assert.assertEquals(2, listaConElems.removeAll("A"));
    	Assert.assertEquals(listaConElems.toString(),"(B C B D )");
    	
        listaConElems.removeAll("B");
		Assert.assertFalse(listaConElems.contains("A"));
		Assert.assertFalse(listaConElems.contains("B"));
		Assert.assertEquals(listaConElems.toString(),"(C D )");
		listaConElems.removeAll("C");
		
		Assert.assertTrue(listaConElems.contains("D"));
		Assert.assertFalse(listaConElems.contains("C"));
        listaConElems.removeAll("D");
		
		Assert.assertFalse(listaConElems.contains("D"));
		Assert.assertTrue(listaConElems.isEmpty());
		Assert.assertTrue(listaConElems.size()==0);
		Assert.assertEquals(listaConElems.toString(),listaConElems.toStringReverse());
		
	}
	
	@Test
	public void isSubListTest() throws EmptyCollectionException {
		 Assert.assertTrue(listaConElems.isSubList(lv));
	    	Assert.assertTrue(listaConElems.isSubList(new DoubleLinkedListImpl<String>("A", "B", "C")));
	      	Assert.assertEquals(listaConElems.toString(),"(A B C A B D )");
	      	Assert.assertEquals(new DoubleLinkedListImpl<String>("A", "C").toString(),"(A C )");   
	     	Assert.assertFalse(listaConElems.isSubList(new DoubleLinkedListImpl<String>("A", "C")));
	     	Assert.assertEquals(listaConElems.maxRepeated(),2);
	     	listaConElems.insertBefore("A", "D");
	    	Assert.assertEquals(listaConElems.toString(),"(A B C A B A D )");
	    	Assert.assertTrue(listaConElems.maxRepeated()==3);
	        	  
	}
	
	@Test
	public void testinsertLast() {
		
		//Si la lista es vacía
		Assert.assertEquals(lv.size(), 0);
		lv.insertLast("A");
		Assert.assertEquals(lv.size(), 1);
		Assert.assertTrue(lv.contains("A"));
		
		//Si la lista tiene un elemento
		lv.insertLast("Z");
		Assert.assertEquals(lv.size(), 2);
		Assert.assertEquals(lv.toString(),"(A Z )");	
		
		//Caso general
		Assert.assertEquals(listaConElems.toString(),"(A B C A B D )");
		listaConElems.insertLast("I");
		Assert.assertEquals(listaConElems.size(), 7);
		Assert.assertEquals(listaConElems.toString(),"(A B C A B D I )");
		
	}
	
	
	@Test
	public void testremoveFirst() throws EmptyCollectionException{
		
		//Si la lista solo tiene 1 elemento
		lv.insertFirst("A");
		Assert.assertEquals(lv.removeFirst(), "A");
		Assert.assertTrue(lv.size() == 0);
		
		//Si la lista tiene 2 o mas 
		Assert.assertEquals(listaConElems.removeFirst(), "A");
		Assert.assertTrue(listaConElems.size() == 5);
			
	}
	
	@Test
	public void testremoveLast() throws EmptyCollectionException{
		
		//Si la lista solo tiene 1 elemento
			lv.insertFirst("A");
			Assert.assertEquals(lv.removeLast(), "A");
			Assert.assertTrue(lv.size() == 0);
				
		//Si la lista tiene 2 o mas 
			Assert.assertEquals(listaConElems.toString(),"(A B C A B D )");
			Assert.assertEquals(listaConElems.removeLast(), "D");
			Assert.assertEquals(listaConElems.toString(),"(A B C A B )");
	}
	@Test
	public void testinsertPos() {
		
		//Si la lista esta vacia e inserto puedo insertar donde quiera que va a ser el primero
		lv.insertPos("H", 10);
		Assert.assertEquals(lv.toString(),"(H )");

		
		//si la lista tiene un elemento e inserto en la 1 (caso de insercion al principio)
		lv.clear();
		lv.insertFirst("A");
		lv.insertPos("Z", 1);
		Assert.assertEquals(lv.toString(),"(Z A )");

		
		//si tiene un elemento e inserto en la 2 (caso de insercion al final)
		lv.clear();
		lv.insertFirst("A");
		lv.insertPos("Z", 2);
		Assert.assertEquals(lv.toString(),"(A Z )");
		
		
		//Si tiene varios elementos e inserto por el medio
		Assert.assertEquals(listaConElems.toString(),"(A B C A B D )");
		listaConElems.insertPos("X", 4);
		Assert.assertEquals(listaConElems.toString(),"(A B C X A B D )");
		
		
	}
	
	
	@Test
	public void testinsertBefore() {
		
		//Si la lista tiene un elemento y es justamente el objetivo
		lv.insertFirst("J");
		lv.insertBefore("Q", "J");
		Assert.assertEquals(lv.toString(),"(Q J )");

		
		//Si la lista tiene dos elementos y el sergundo es el objetivo
		lv.insertBefore("H", "J");
		Assert.assertEquals(lv.toString(),"(Q H J )");

		
		//si la lista tiene muchos elementos y cae por el medio
		listaConElems.insertPos("X", 4);
		listaConElems.insertBefore("X", "X");
		Assert.assertEquals(listaConElems.toString(),"(A B C X X A B D )");
		
		//ahora hay varias apariciones del elemento en la lista
		listaConElems.insertBefore("L", "B");
		Assert.assertEquals(listaConElems.toString(),"(A L B C X X A B D )");

		
	}
	
	@Test
	public void testgetElemenPos() {
		
		//Si la lista tiene un elemento
		lv.insertFirst("A");
		Assert.assertEquals(lv.getElemPos(1), "A");
			
		//Si la lista tiene 2 elementos y quiero el segundo
		lv.insertFirst("A");
		Assert.assertEquals(lv.getElemPos(2), "A");
		
		//caso general
		Assert.assertEquals(listaConElems.getElemPos(6), "D");

		
	}
	
	
	@Test
	public void testgetPosFirst() {
		
		//Pongo 1 elemento que justo es el que quiero
		lv.insertFirst("A");
		Assert.assertEquals(lv.getPosFirst("A"), 1);
		
		//Pongo 2 elementos iguales en la lista para ver que me da la primera
		lv.insertFirst("A");
		Assert.assertEquals(lv.getPosFirst("A"), 1);
		
		//Solo 1 aparicion y es la ultima
		Assert.assertEquals(listaConElems.getPosFirst("D"), 6);

		
		//En un caso general pongo 3 por el medio a ver si me da la primera
		listaConElems.insertPos("C", 3);
		listaConElems.insertPos("C", 3);
		//Assert.assertEquals(listaConElems.toString(),"(A B C C C A B D )");
		Assert.assertEquals(listaConElems.getPosFirst("C"), 3);

		
	}
	
	
	@Test
	public void testgetPosLast() {
		
		//Pongo 1 elemento que justo es el que quiero
		lv.insertFirst("L");
		Assert.assertEquals(lv.getPosLast("L"), 1);
		
		//Pongo 2 elementos iguales en la lista para ver que me da la ultima
		lv.insertLast("L");
		Assert.assertEquals(lv.getPosLast("L"), 2);

		//Solo 1 aparicion y es la ultima
		Assert.assertEquals(listaConElems.getPosLast("D"), 6);

		
		//En un caso general pongo 3 por el medio a ver si me da la ultima
		Assert.assertEquals(listaConElems.getPosLast("B"), 5);
		listaConElems.insertPos("C", 3);
		listaConElems.insertPos("C", 3);
		Assert.assertEquals(listaConElems.toString(),"(A B C C C A B D )");
		Assert.assertEquals(listaConElems.getPosLast("C"), 5);
		
		//Pongo 2 en los extremos de la lista
		lv.insertPos(">", 1);
		lv.insertPos(">", lv.size()+1); //sino me lo inserta delante de la ultima
		Assert.assertEquals(lv.getPosLast(">"), lv.size());

		
	}
	
	
	@Test	//ESPERAR CORREO PARA VER SI PUEDO REUSAR REMOVE FIRST Y LAST
	public void testremovePos() {
		
		//vamos a eliminar el unico elemento de la lista
		lv.insertFirst("B");
		Assert.assertEquals(lv.size(), 1);
		Assert.assertEquals(lv.removePos(1), "B");
		Assert.assertEquals(lv.size(), 0);
		
		//si tiene 2 elementos vamos a eliminar el segundo
		lv.insertFirst("B");
		lv.insertFirst("A");
		Assert.assertEquals(lv.size(), 2);
		Assert.assertEquals(lv.removePos(2), "B");
		Assert.assertEquals(lv.size(), 1);
		
		//en un caso general voy a quitar uno del medio
		Assert.assertEquals(listaConElems.removePos(4), "A");
		Assert.assertEquals(listaConElems.size(), 5);
		
		//en un caso general en el que voy a quitar el primero
		Assert.assertEquals(listaConElems.removePos(1), "A");
		Assert.assertEquals(listaConElems.size(), 4);
		
		//voy a eleminar el ultimo en un caso general
		Assert.assertEquals(listaConElems.removePos(4), "D");
		Assert.assertEquals(listaConElems.size(), 3);
		
	}
	
	
	@Test    //ESPERAR CORREO PARA VER SI PUEDO REUSAR REMOVE FIRST Y LAST
	public void testremoveAll() {
		//Si solo hay un elemento y lo quito
		lv.insertFirst("A");
		Assert.assertEquals(lv.size(), 1);
		Assert.assertEquals(lv.removeAll("A"), 1);
		Assert.assertEquals(lv.size(), 0);


		//si toda la lista es de elementos iguales
		lv.insertFirst("A");
		lv.insertFirst("A");
		lv.insertFirst("A");
		lv.insertFirst("A");
		lv.insertFirst("A");
		lv.insertFirst("A");
		Assert.assertEquals(lv.size(), 6 );
		Assert.assertEquals(lv.removeAll("A"), 6);
		Assert.assertEquals(lv.size(), 0);


		//Si los dos ultimos son iguales
		listaConElems.insertLast("D");
		listaConElems.insertLast("D");
		Assert.assertEquals(listaConElems.removeAll("D"), 3);

		Assert.assertEquals(listaConElems.size(), 5);

		
		//Si hay varios iguales desperdigados por la lista
		listaConElems.insertPos("A", 3);
		listaConElems.insertLast("A");
		Assert.assertEquals(listaConElems.removeAll("A"), 4);
		Assert.assertEquals(listaConElems.size(), 3);
		Assert.assertEquals(listaConElems.toString(),"(B C B )");

	}
	
	@Test
	public void testcontains() {
		//lista vacia
		Assert.assertFalse(lv.contains("A"));
		
		//lista mas de una instancia
		Assert.assertTrue(listaConElems.contains("A"));
		
		//el ultimo
		Assert.assertTrue(listaConElems.contains("D"));
		
		//el primero
		listaConElems.insertFirst("X");
		Assert.assertTrue(listaConElems.contains("X"));

		//ninguno
		Assert.assertFalse(listaConElems.contains("W"));
		
	}
	
	@Test
	public void testsize() {
		//lista vacia
		Assert.assertEquals(lv.size(), 0);
		
		//lista con 1 elemento
		lv.insertFirst("A");
		Assert.assertEquals(lv.size(), 1);

		//lista con 6 elementos
		Assert.assertEquals(listaConElems.size(), 6);
		
		
	}
	
	@Test
	public void testtoStringReverse() {
		//Normal
		
		Assert.assertEquals(listaConElems.toString(), "(A B C A B D )" );
		
		//Del reves
		
		Assert.assertEquals(listaConElems.toStringReverse(), "(D B A C B A )" );
		
		//Lista vacia
		
		Assert.assertEquals(lv.toStringReverse(), "()" );

	}

	@Test
	public void testreverse() {
		//veo el tostring normal
		Assert.assertEquals(listaConElems.toString(), "(A B C A B D )" );
		
		//creo la lista en donde le voy a dar la vuelta
		DoubleList<String> listaReversa = new DoubleLinkedListImpl<String>(); //se crea una lista nueva
		
		//Le doy la vuelta a la lista		
		listaReversa = listaConElems.reverse();
		
		//miro el tostring
		Assert.assertEquals(listaReversa.toString(), "(D B A C B A )" );

		//compruebo que es igual que lo que nos das el tostring del reves
		Assert.assertEquals(listaConElems.toStringReverse(), listaReversa.toString());

		
	}
	
	@Test
	public void testmaxRepeated() {
		
		//si la lista es vacia veo que da 0
		Assert.assertEquals(lv.maxRepeated(), 0);
		
		//si la lista tiene 1 elemento compruebo que es 1
		lv.insertFirst("A");
		Assert.assertEquals(lv.maxRepeated(), 1);

		//Si el numero de repeticiones es igual
		Assert.assertEquals(listaConElems.maxRepeated(), 2);
		

		//si tiene 2 o mas veo que efectivamente da el numero máximo de repeticiones
		listaConElems.insertFirst("A");
		listaConElems.insertFirst("A");
		Assert.assertEquals(listaConElems.maxRepeated(), 4);
	}
	
	@Test
	public void testisEquals() {
		
		//2 listas vacias
		Assert.assertTrue(lv.isEquals(lv));
		
		//2 listas con 1 elemento pero diferente tipo
		lv.insertFirst("A");
		DoubleLinkedListImpl listaConNum=new DoubleLinkedListImpl<Integer>(); //se crea una lista nueva
		listaConNum.insertFirst(1);
		Assert.assertFalse(lv.isEquals(listaConNum));
		
		//dos listas diferentes
		Assert.assertFalse(listaConElems.isEquals(lv));
		Assert.assertFalse(lv.isEquals(listaConElems));
		
		//dos listas iguales
		Assert.assertTrue(listaConElems.isEquals(listaConElems));
		
		//ultimo elemento desigual
		lv.clear();
		lv.insertFirst("B");
		lv.insertFirst("B");
		lv.insertFirst("A");
		lv.insertFirst("C");
		lv.insertFirst("B");
		lv.insertFirst("A");
		Assert.assertFalse(lv.isEquals(listaConElems));
			
	}
	
	@Test
	public void testcontainsAll() {
		//Si la lista es vacia o las dos son vacias ambas contienen el elemento conjunto vacío
		Assert.assertTrue(listaConElems.containsAll(lv));
		Assert.assertTrue(lv.containsAll(lv));
		
		//Si las dos listas tienen 1 elemento igual
		lv.insertFirst("A");
		Assert.assertTrue(lv.containsAll(lv));
		
		//si una lista es mas pequeña que otra
		lv.insertFirst("D");
		lv.insertFirst("B");
		lv.insertFirst("C");
		
		Assert.assertFalse(lv.containsAll(listaConElems)); //la lista pequeña no contiene a la grande
		Assert.assertTrue(listaConElems.containsAll(lv)); //la lista grande contiene a la pequeña
		
		//El elemento del medio no lo contiene
		lv.clear();
		lv.insertLast("B");
		lv.insertLast("X");
		lv.insertLast("A");
		Assert.assertFalse(listaConElems.containsAll(lv));
		
	}
	
	@Test
	public void testisSubList() {
		
		//Lista vacia si tiene que ser sublista
		Assert.assertTrue(listaConElems.isSubList(lv));
		
		//lista con un elemento que si se contiene
		lv.insertFirst("C");
		Assert.assertTrue(listaConElems.isSubList(lv));

		
		//con una sublista que este al final
		lv.clear();
		lv.insertFirst("D");
		lv.insertFirst("B");
		lv.insertFirst("A");
		Assert.assertTrue(listaConElems.isSubList(lv));

		
		//Sublista que esta mal (y otra desperdigada)
		lv.clear();
		lv.insertFirst("X");
		lv.insertFirst("B");
		lv.insertFirst("A");
		Assert.assertFalse(listaConElems.isSubList(lv));
		lv.clear();
		lv.insertFirst("B");
		lv.insertFirst("C");
		lv.insertFirst("B");
		Assert.assertFalse(listaConElems.isSubList(lv));
		
		//una sublista mas grande que la original
		
		lv.clear();
		lv.insertLast("A");
		lv.insertLast("B");
		lv.insertLast("C");
		lv.insertLast("A");
		Assert.assertFalse(lv.isSubList(listaConElems));


		
	}
	
	@Test
	public void testtoStringFromUntil() {
		
		//lista vacia
		Assert.assertEquals(lv.toStringFromUntil(1, 1), "()");
		
		
		//solo el ultimo elemento
		Assert.assertEquals(listaConElems.toStringFromUntil(6, 6), "(D )");
		Assert.assertEquals(listaConElems.toStringFromUntil(4, 6), "(A B D )");

		//un trozo de la lista
		Assert.assertEquals(listaConElems.toStringFromUntil(2, 4), "(B C A )");
		
		//until > size
		Assert.assertEquals(listaConElems.toStringFromUntil(1,20), "(A B C A B D )");
		Assert.assertEquals(listaConElems.toStringFromUntil(4,20), "(A B D )");

		
		//from > size
		Assert.assertEquals(listaConElems.toStringFromUntil(30, 40), "()");
		
		
	}
	
	@Test
	public void testtoString() {
		//Lista vacia
		Assert.assertEquals(lv.toString(), "()" );

		//Lista llena
		Assert.assertEquals(listaConElems.toString(), "(A B C A B D )" );
	
	}
	
	
		/* Test de los iteradores */
	
	@Test
	public void testiteradorNormal() {
		
		ArrayList<String> listaPrueba = new ArrayList<String>();
		
		//creo un iterador lista normal
		
		Iterator<String> iterador = listaConElems.iterator();
		
		while(iterador.hasNext() == true) {
			listaPrueba.add(iterador.next());
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[A, B, C, A, B, D]");
		
		//Caso extremo en el que se llama al next una vez acabada la lista
		
		listaPrueba.add(iterador.next());

				
		Assert.assertEquals(listaPrueba.toString(), "[A, B, C, A, B, D, null]");
		
		//lo pruebo con una lista vacia 
		
		listaPrueba.clear();
		Iterator<String> iteradorV = lv.iterator();


		while(iteradorV.hasNext() == true) {
			listaPrueba.add(iteradorV.next()); //como es vacia no llega a entrar aqui porque el hasnext da falso
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[]");
		
		//Pruebo con una lista que tenga un elemento
		lv.insertFirst("A");
		
		listaPrueba.clear();

		iterador = lv.iterator(); //vuelvo a obtener el iterador


		while(iterador.hasNext() == true) {
			listaPrueba.add(iterador.next());
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[A]");
	}
	
	@Test
	public void testiteradorDelReves() {
		
		ArrayList<String> listaPrueba = new ArrayList<String>();
		
		//creo un iterador lista normal
		
		Iterator<String> iterador = listaConElems.reverseIterator();
		
		while(iterador.hasNext() == true) {
			listaPrueba.add(iterador.next());
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[D, B, A, C, B, A]");
		
		//Caso extremo en el que se llama al next una vez acabada la lista
		
		listaPrueba.add(iterador.next());

		
		Assert.assertEquals(listaPrueba.toString(), "[D, B, A, C, B, A, null]");
		

		
		//lo pruebo con una lista vacia 
		
		listaPrueba.clear();
		Iterator<String> iteradorV = lv.reverseIterator();


		while(iteradorV.hasNext() == true) {
			listaPrueba.add(iteradorV.next()); //como es vacia no llega a entrar aqui porque el hasnext da falso
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[]");
		
		//Pruebo con una lista que tenga un elemento
		lv.insertFirst("A");
		
		listaPrueba.clear();

		iterador = lv.reverseIterator(); //vuelvo a obtener el iterador


		while(iterador.hasNext() == true) {
			listaPrueba.add(iterador.next());
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[A]");
	}

	
	@Test //NO VA
	public void testiteradorPar() {
		
	ArrayList<String> listaPrueba = new ArrayList<String>();
		
		//creo un iterador lista normal
		
		Iterator<String> iterador = listaConElems.evenPositionsIterator();
		
		while(iterador.hasNext() == true) {
			listaPrueba.add(iterador.next());
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[B, A, D]");
		
		//Caso extremo en el que se llama al next una vez acabada la lista
		
		listaPrueba.add(iterador.next());

		
		Assert.assertEquals(listaPrueba.toString(), "[B, A, D, null]");
		
		//Lista Impar
		listaPrueba.clear();
		 lv.insertFirst("A");
		 lv.insertFirst("B");
		 lv.insertFirst("C");
		
		iterador = lv.evenPositionsIterator();


		 while(iterador.hasNext() == true) {
				listaPrueba.add(iterador.next());
			}
			
		Assert.assertEquals(listaPrueba.toString(), "[B]");
		
		
		//lo pruebo con una lista vacia 
		lv.clear();

		listaPrueba.clear();
		Iterator<String> iteradorV = lv.evenPositionsIterator();


		while(iteradorV.hasNext() == true) {
			listaPrueba.add(iteradorV.next()); //como es vacia no llega a entrar aqui porque el hasnext da falso
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[]");
		
		//Pruebo con una lista que tenga un elemento
		lv.insertFirst("A");
		
		listaPrueba.clear();

		iterador = lv.evenPositionsIterator(); //vuelvo a obtener el iterador


		while(iterador.hasNext() == true) {
			listaPrueba.add(iterador.next());
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[null]");
		
	}
	
	
	@Test
	public void testiteradorProgresivo() throws EmptyCollectionException {
		
		ArrayList<String> listaPrueba = new ArrayList<String>();
		
		//creo un iterador lista normal
		
		Iterator<String> iterador = listaConElems.progressIterator();


		listaPrueba.add(iterador.next()); //A
		listaPrueba.add(iterador.next()); //B
		listaPrueba.add(iterador.next()); //A
		
	
		Assert.assertEquals(listaPrueba.toString(), "[A, B, A]");

		//Caso extremo en el que se llama al next una vez se que el numero de saltos (3) es mayor a lo que abarca la lista
		
		listaPrueba.add(iterador.next());  //no hay mas porque la lista no es suficientemente larga

		
		Assert.assertEquals(listaPrueba.toString(), "[A, B, A, null]");
		
		
		//quito 2 elementos para que no le vaya tan bien y caiga el null en el proceso de preparar la estructura de datos del iterador para la siguiente
		
		listaConElems.removeFirst();
		listaConElems.removeFirst();

		iterador = listaConElems.progressIterator();


		listaPrueba.add(iterador.next()); //A
		listaPrueba.add(iterador.next()); //B
		listaPrueba.add(iterador.next()); //A
		listaPrueba.add(iterador.next()); //en esta ya va a caer el nodoActual en null antes de acabar el for

		
		//lo pruebo con una lista vacia 
		
		listaPrueba.clear();
		Iterator<String> iteradorV = lv.progressIterator();


		while(iteradorV.hasNext() == true) {
			listaPrueba.add(iteradorV.next()); //como es vacia no llega a entrar aqui porque el hasnext da falso
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[]");
		
		//Pruebo con una lista que tenga un elemento
		lv.insertFirst("A");
		
		listaPrueba.clear();

		iterador = lv.progressIterator(); //vuelvo a obtener el iterador


		while(iterador.hasNext() == true) {
			listaPrueba.add(iterador.next());
		}
		
		Assert.assertEquals(listaPrueba.toString(), "[A]");

		
	}
	

	
		/* TEST DE EXCEPTIONS */
	
	@Test (expected = NullPointerException.class)
	public void testinsertFirstNullPointerException() { 
	
		lv.insertFirst(null);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testinsertLastNullPointerException() {
		
		lv.insertLast(null);
		
	}
	
	@Test(expected = EmptyCollectionException.class)
	public void testremoveFirstEmptyCollectionException() throws EmptyCollectionException {
		
		lv.removeFirst();
		
	}
	
	@Test(expected = EmptyCollectionException.class)
	public void testremoveLastEmptyCollectionException() throws EmptyCollectionException{
		
		lv.removeLast();
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testinsertPosNullPointerException() {
		
		listaConElems.insertPos(null, 3);
	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testinsertPosIllegalArgumentException() {
		
		listaConElems.insertPos("B", -1);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testinsertBeforeNullPointerEceptionElem(){
	
		listaConElems.insertBefore(null, "D");
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testinsertBeforeNullPointerEceptionTarget(){
	
		listaConElems.insertBefore("J", null);
		
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testinsertBeforeNoSuchElementExceptionEmpty() {
		
		lv.insertBefore("A", "B");
				
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testinsertBeforeNoSuchElementExceptionNotFound() {
		
		listaConElems.insertBefore("A", "X");
				
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testgetElemPosIllegalArgumentException() {
		
		listaConElems.getElemPos(-5);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testgetElemPosIllegalArgumentException2() {
		
		listaConElems.getElemPos(7);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testgetPosFirstNullPointerException() {
		
		listaConElems.getPosFirst(null);
		
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testgetPosFirstNoSuchElementExceptionNotFound() {
		
		listaConElems.getPosFirst("W");
	
	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testgetPosFirstNoSuchElementExceptionEmpty() {
		
		lv.getPosFirst("W");

	}
	
	@Test(expected = NullPointerException.class)
	public void testgetPosLastNullPointerException() {
		
		listaConElems.getPosLast(null);
		
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testgetPosLastNoSuchElementExceptionNotFound() {
		
		listaConElems.getPosLast("W");
	
	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testgetPosLastNoSuchElementExceptionEmpty() {
		
		lv.getPosLast("W");

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testremovePosIllegalArgumentExceptionLower() {
		
		listaConElems.removePos(-2);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testremovePosIllegalArgumentExceptionZero() {
		
		listaConElems.removePos(0);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testremovePosIllegalArgumentExceptionHigher() {
		
		listaConElems.removePos(7);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testremoveAllNullPointerException() {
		
		listaConElems.removeAll(null);
		
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testremoveAllNoSuchElementExceptionNotFound() {
		
		listaConElems.removeAll("W");
	
	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testremoveAllNoSuchElementExceptionEmpty() {
		
		lv.removeAll("W");

	}
	
	@Test(expected = NullPointerException.class)
	public void testcontainsNullPointerException() {
		
		listaConElems.contains(null);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testisEqualsNullpointerException() {
		
		listaConElems.isEquals(null);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testcontainsAllNullpointerException() {
		
		listaConElems.containsAll(null);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testisSublistNullpointerException() {
		
		listaConElems.isSubList(null);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testtoStringFromUntilIllegalArgumentExceptionFromLower() {
		
		listaConElems.toStringFromUntil(-1, 3);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testtoStringFromUntilIllegalArgumentExceptionZero() {
		
		listaConElems.toStringFromUntil(0, 3);

		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testtoStringFromUntilIllegalArgumentExceptionUntilLower() {
		
		listaConElems.toStringFromUntil(1, -2);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testtoStringFromUntilIllegalArgumentExceptionZero2() {
		
		listaConElems.toStringFromUntil(2, 0);

		
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testtoStringFromUntilIllegalArgumentExceptionSwitched() {
		
		listaConElems.toStringFromUntil(4, 2);

		
	}
	
	
	
	
	
	
	
	
	
	
}
