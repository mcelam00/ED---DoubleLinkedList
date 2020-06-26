package ule.edi.doubleLinkedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import ule.edi.exceptions.EmptyCollectionException;

public class DoubleLinkedListImpl<T> implements DoubleList<T> { //lista doblemente enlazada
	
	
	//	referencia al primer nodo de la lista
	private DoubleNode<T> front;
	
	//	referencia al Ãšltimo nodo de la lista
	private DoubleNode<T> last;
	
	
	private class DoubleNode<T> { //clase interna que representa cada nodo de la lista
		
		DoubleNode(T element) {
			this.elem = element; //construye un nodo con el elemento que se le pasa
			this.next = null;
			this.prev = null;
		}
		
		T elem; //objeto de tipo generico que almacena el nodo
		
		DoubleNode<T> next; //referencia al siguiente nodo
	    DoubleNode<T> prev; //referencia al nodo anterior
	}

///// ITERADOR normal //////////

	@SuppressWarnings("hiding")
	private class DobleLinkedListIterator<T> implements Iterator<T> {
		  private DoubleNode<T> nodoActual; 
		
       	
		public DobleLinkedListIterator(DoubleNode<T> nodo) {
			nodoActual = nodo;			
		}
		
		@Override
		public boolean hasNext() {
			
			boolean testigo = true;
			
			if(nodoActual == null) {
				testigo = false;
			}
			
			return testigo;
		}

		@Override
		public T next() {
			T elemento = null;

			if(hasNext() == true) {
				elemento = this.nodoActual.elem;
				nodoActual = nodoActual.next;
				
			}
			return elemento;
		}

		
	}
	
	////// FIN ITERATOR
	
	
///// ITERADOR del revés //////////

	@SuppressWarnings("hiding")
	private class DobleLinkedListreverseIterator<T> implements Iterator<T> {
		  private DoubleNode<T> nodoActual; 
		
       	
		public DobleLinkedListreverseIterator(DoubleNode<T> nodo) {
			nodoActual = nodo;			
		}
		
		@Override
		public boolean hasNext() {
			
			boolean testigo = true;
			
			if(nodoActual == null) {
				testigo = false;
			}
			
			return testigo;
		}

		@Override
		public T next() {
			T elemento = null;
		
			if(hasNext() == true) {
			
				elemento = this.nodoActual.elem;
				nodoActual = nodoActual.prev;
				
			}
			return elemento;
		}

		
	}
	
	////// FIN ITERATOR
	
///// ITERADOR posiciones par //////////

	@SuppressWarnings("hiding")
	private class DobleLinkedListevenPositionsIterator<T> implements Iterator<T> {
		private DoubleNode<T> nodoActual;
		private int pos;

       	
		public DobleLinkedListevenPositionsIterator(DoubleNode<T> nodo) {
			
			nodoActual = nodo;
			pos = 1;
		
		}
		
		@Override
		public boolean hasNext() {
			boolean testigo = true;
			
			if(nodoActual == null) {
				testigo = false;
			}
			
			return testigo;
			
		
		}

		@Override
		public T next() {
			
			T elemento = null;
			
			//voy a comprobar que donde está la referencia actual del iterador no es null (p.e lista vacia)
			if(hasNext() == true) {
				
				if(pos == 1) {  
					
					this.nodoActual = this.nodoActual.next; //muevo la referencia 1 lugar porque empiezo en front
					pos++; //me aseguro de que no se vuelve a entrar
										
				}
				
				//Caso general
				if(hasNext() == true) { //Por si la lista tiene 1 solo elemento
					
					elemento = this.nodoActual.elem; //guardo el elemento
					
					//preparo la estructura de datos del iterador a la siguiente posicion par
					
					this.nodoActual = this.nodoActual.next; 
					
					if(hasNext() == true) { //para mover una segunda vez, primero me cercioro de que no estoy ya en null al moverme una
						
						this.nodoActual = this.nodoActual.next; 
	
					}
					
					//Ya queda preparada para el siguiente
				}
							
			}	
			
			return elemento;

		}

		
	}
	
	////// FIN ITERATOR
	
///// ITERADOR progresivo //////////

	@SuppressWarnings("hiding")
	private class DobleLinkedListprogressIterator<T> implements Iterator<T> {
		 private int numSaltos;
		 private DoubleNode<T> nodoActual; 

       	
		public DobleLinkedListprogressIterator(DoubleNode<T> nodo) {
			this.nodoActual = nodo;
			this.numSaltos = 1;
						
		}
		
		@Override
		public boolean hasNext() { //Mira si hay siguiente salto
			
			boolean testigo = true;
			
			if(nodoActual == null) {
				testigo = false;
			}
			
			return testigo;
			
		}

		@Override
		public T next() { //salta al siguiente segun corresponda por la vez que sea y si se puede

			T elemento = null;
			
			if(hasNext() == true) { //si es vacia o el numero de saltos no se va a poder completar

				
				//retorna el elemento
				elemento = this.nodoActual.elem;
				
				
				for(int i = 1; i <= numSaltos; i++) { //lo deja preparado para el siguiente next
					if(hasNext() == false) {
						break; //por si al iterar de pronto pasa a ser null la posicion
					}
					this.nodoActual = this.nodoActual.next; //muevo a la posicion que toca segun los saltos
					
				}
				this.numSaltos++; //incremento el numero de saltos en cada llamada al next


			}
			
			return elemento;

			
			//si no hay elemento en esa posicion no se retorna nada
			
		
			
		}

		
	}
	
	////// FIN ITERATOR
	
    /////
	
	
	@SafeVarargs
	public DoubleLinkedListImpl(T...v ) { //recibe elementos de tipo T y crea una lista con ellos (method parameter of variable length)
		for (T elem:v) {
			this.insertLast(elem);
		}
	}


	@Override
	public boolean isEmpty() {
		
		boolean testigo = false;
		
		//La lista es vacia cuando no hay nodos, es decir, front y last apuntan a null, pero con que front apunte a null ya no hay lista doblemente enlazada de donde empezar
		
		if(this.front == null) {
			
			testigo = true;
			
		}
		
		
		return testigo;		
		
	}


	@Override
	public void clear() {
		
		//Para dejar la lista vacía la suelto, es decir, le quito el front y el last de los nodos ultimo y primero y los pongo a null
		
		this.front = null;
		this.last = null;
		
		
	}


	@Override
	public void insertFirst(T elem) {
		
		/* Comprobacion de la entrada */
		
		if(elem == null) {
			throw new NullPointerException();
		}
		
		//Creamos el nuevo nodo
		DoubleNode<T> nuevo = new DoubleNode<T>(elem);
				
		if(isEmpty() == true) { //si es vacia tanto front como rear apuntan al nuevo porque no hay mas
			this.front = nuevo;
			this.last = nuevo;
		}
		else {

			nuevo.next = this.front;
			this.front.prev = nuevo; //ya tengo enganchado el nuevo nodo
			
			//muevo el front
			
			this.front = nuevo;		
			
			//al crear el nodo nuevo ya apunta a null su prev			
			
		}		
	}


	@Override
	public void insertLast(T elem) {
		
		/* Comprobacion de la entrada */
		
		if(elem == null) {
			throw new NullPointerException();
		}
		
		DoubleNode<T> nuevo = new DoubleNode<T>(elem);
		
		if(isEmpty() == true) { //si es vacia, front y last apuntan al nuevo porque no hay principio ni fin
			this.last = nuevo;
			this.front = nuevo;			
		}
		else {
			
			nuevo.prev = this.last; //engancho el nodo por el final de la lista
			this.last.next = nuevo;
			
			
			this.last = nuevo; //muevo el last al nuevo ultimo de la lista
			
			//el nuevo.next no hace falta ponerlo a null, porque ya esta
			
		}
	
	}


	@Override
	public T removeFirst() throws EmptyCollectionException{
		
		if(isEmpty() == true){
			throw new EmptyCollectionException("DoubleLinkedList");
		}
		
		//Necesito salvar el elemento
		T elemento = null;
		elemento = this.front.elem; //guardo el elemento

		
		if(size() == 1) { //si la lista solamente tiene un elemento

			clear(); //borro toda la lista
			
		}
		else {
			
			//mover el front y una vez movido apuntar con el prev del nuevo front a null para desenganchar el antiguo primero
			this.front = this.front.next;
			this.front.prev = null; 
						
			
		}
		
		
		return elemento;
		
	}


	@Override
	public T removeLast()  throws EmptyCollectionException{
	
		if(isEmpty() == true) {
			throw new EmptyCollectionException("DoubleLinkedList");
		}
		//vamos a guardarlo 
		T elemento;
		elemento = this.last.elem;
		
		if(size() == 1) {
			clear(); //quito el elemento que tiene
		}
		else {
			
			//voy a mover el last al last.prev y poner el last next nuevo a null
			this.last = this.last.prev;
			this.last.next = null;
						
		}
		
		return elemento;	
		
	}


	@Override
	public void insertPos(T elem, int position) {
		
		/* Comprobacion de la entrada */
		if(elem == null) {
			throw new NullPointerException();
		}
		if(position <= 0) {
			throw new IllegalArgumentException();
		}
		
		DoubleNode<T> nuevo = new DoubleNode<T>(elem);
		
		if(isEmpty() == true) { //si la lista esta vacia me da igual la posicion
			
			insertFirst(elem);
			
		}
		else {
			
			//si no esta vacia voy a comprobar que la posicion no se va de bounds, si fuera mayor al size lo insertamos como ultimo 
			if(position > size()) {

				insertLast(elem);
				
			}
			else if(position == 1) { //si pos es la primera, no es igual que otros casos porque se insertaria como primero, no entre dos nodos como en el resto de los casos
				
				insertFirst(elem);
								
			}
			else { //es que se inserta en el medio de algun nodo
				
				DoubleNode<T> aux = this.front.next; //voy a empezar en la dos porque el caso de la 1 ya lo he atajado		
				int contador = 2;
				
				while(aux != null) { //necesito recorrer la lista hasta que aux caiga en la posicion que me estan diciendo
					
					if(contador == position) { //si es la posicion perfecto
						//voy a insertarlo delante del nodo que indica la posicion
						nuevo.prev = aux.prev;
						nuevo.next = aux;			//ya esta el nodo enganchado ahora arreglo los que estaban dentro
						
						aux.prev.next = nuevo; //siendo que prev no apunta aun al nuevo aprovecho para no tener que hacer prev prev next
						aux.prev = nuevo;
											
					}
			
					contador++;
					aux = aux.next; //muevo en sincronia el contador y el aux y así en el momento en que sea la posicion el aux esta justo en el nodo
				}
				
			}
						
		}
		
		
	}


	@Override
	public void insertBefore(T elem, T target) {
		
		/* Comprobacion Entrada */
		
		if(elem == null || target == null) {
			throw new NullPointerException();
		}
			
		//Busco el elemento que me pasan, cuando tengo la posicion del elemento que se me pasa se la mando al metodo insertPos que inserta delante de la posicion indicada
		
		
		if(contains(target) == true) {  //miramos a ver si lo contiene, si no, exception
				//busco su posicion
			DoubleNode<T> aux = this.front;
			int contador = 1;
		
			while(target.equals(aux.elem) == false) { //como sé que lo contiene y quiero la primera ocurrencia
				
				aux = aux.next;
				contador++;
			}
			//sé que el equals se ha validado y aux está en el elemento que es igual al target y en contador tengo su posicion
			
			insertPos(elem, contador);
			

		}
		else {
			throw new NoSuchElementException();
		}
				
		
		
		
	}


	@Override
	public T getElemPos(int position) {
		
		/* Comprobacion Entrada */

		if(position <= 0 || position > size()) {
			throw new IllegalArgumentException();
		}
		
		//sabemos que está entre los limites de la lista
		
		DoubleNode<T> aux = this.front;
		
		for(int i = 1; i < position; i++ ) { //recorro la lista con el aux pero no hasta el final sino hasta que la posicion sea la indicada y tendré el aux en el nodo de la pos indicada
			aux = aux.next;					
		}
		
		return aux.elem; //retorno el elemento de la posicion buscada	
		
	}


	@Override
	public int getPosFirst(T elem) {
	
		/* Comprobacion Entrada */

		if(elem == null ) {
			throw new NullPointerException();
		}
			
			
		if(contains(elem) == true) {  //miramos a ver si lo contiene, si no, exception
				//busco su posicion
			DoubleNode<T> aux = this.front;
			int contador = 1;
		
			while(elem.equals(aux.elem) == false) { //como sé que lo contiene y quiero la primera ocurrencia
				
				aux = aux.next;
				contador++;
			}
			//sé que el equals se ha validado y aux está en el elemento que es igual al target y en contador tengo su posicion
			
			return contador;
			

		}
		else {
			throw new NoSuchElementException();
		}
			
		
		
	}


	@Override
	public int getPosLast(T elem) {
		
		/* Comprobacion Entrada */

		if(elem == null ) {
			throw new NullPointerException();
		}
			
	
		
		if(contains(elem) == true) {  //miramos a ver si lo contiene, si no, exception
				//busco su posicion
			DoubleNode<T> aux = this.front;
			int contador = 1;
			int posicion = 0;
		
			while(aux != null) { //recorro toda la lista enlazada y siempre que coincida el elemento guardo la posicion por la que voy y al recorrer toda siempre saldré con la ultima
				
				if(elem.equals(aux.elem)) {
					posicion = contador;
				}
				
				aux = aux.next;
				contador++;
			}
			
			
			return posicion;
			

		}
		else {
			throw new NoSuchElementException();
		}
				
	}


	@Override
	public T removePos(int pos) {
		
		/* Comprobacion Entrada */
		if(pos <= 0 || pos > size()) {
			throw new IllegalArgumentException();
		} 
		
		T elemento = null;
		
		
		if(pos == size()) { //si es size, elimino el ultimo o el primero si solo tene un elemento la lista
			
			elemento = this.last.elem;
			
			if(size() == 1) { // si solo tiene un elemento, los ultimos seran los primeros
				clear(); 
			}
			else { //si tiene mas es el ultimo
				this.last = this.last.prev;
				this.last.next = null;		
			}

		
		}
		else if(pos == 1) { //si es 1 la pos necesito eliminar el primero, pero el caso de 1 solo elemento lo cubrí antes
			
			elemento = this.front.elem;				
			
			//mover el front y una vez movido apuntar con el prev del nuevo front a null para desenganchar el antiguo primero
			this.front = this.front.next;
			this.front.prev = null; 

		}
		else {
			
			//sabiendo que es una posicion que esta dentro de la lista
			//busco la posicion
			
			DoubleNode<T> aux = this.front;
			
			for(int i = 1; i < pos; i++ ) { //recorro las casillas hasta posicion
				aux = aux.next;
				
			}
			//ya tengo mi aux en el nodo posicion: lo elimino
			elemento = aux.elem;
			
			aux.prev.next = aux.next;
			aux.next.prev = aux.prev;
			
		}
		
		return elemento;
	}


	@Override
	public int removeAll(T elem) {
	
		/* Comprobacion Entrada */

		if(elem == null) {
			throw new NullPointerException();
		}
		
		int numeroInstancias = 0;
					
			//Voy a buscar la posicion del elemento 
	
			DoubleNode<T> aux = this.front;
				
		
			while(aux != null) {
				
				if(elem.equals(aux.elem)) { 
					
					if(aux == this.front) { //si apunta al mismo sitio que front es que encontró en la primera y ojo!
						
						if(size() == 1) { 

							clear(); //borro toda la lista
	
						}
						else {
							this.front = this.front.next;
							this.front.prev = null; 
	
						}
						
						numeroInstancias++;
						
					}else if(aux == this.last) { //si la flrcha de aux en este momento es la misma que la de rear
							//Si se diera el caso de que solo hubiese 1 elemento el front == rear con lo cual ya en la anterior hubiera entreado y se hubiera eliminado
							this.last = this.last.prev;
							this.last.next = null;				
			
						numeroInstancias++;
						
					}else {
					
						aux.next.prev = aux.prev;
						aux.prev.next = aux.next;
						
						numeroInstancias++;
					}
				}
				
				aux = aux.next;
			}
			
			if(numeroInstancias == 0) { //tanto si la lista es vacia como si la recorre toda y no esta
				throw new NoSuchElementException();
			}
		
			
		return numeroInstancias;
		
	}


	@Override
	public boolean contains(T elem) {
		
		/* Comprobacion Entrada */
		if(elem == null) {
			throw new NullPointerException();
		}
		
		boolean testigo = false;
		DoubleNode<T> aux = this.front;
		
		while(aux != null) {
			if(elem.equals(aux.elem)) {  //elem.equals porque elem sabemos que no es null pero quiza el elemento de la posicion de la lista si sea null
				testigo = true;
				break;
			}			
			aux = aux.next;
		}
		
		return testigo;		
	}


	@Override
	public int size() {
		
		int size = 0;
				
		DoubleNode<T> aux = this.front;
		
		while(aux != null) {
			
			size++;			
			aux = aux.next;
		}
			
		return size;		
			
			
	}


	@Override
	public String toStringReverse() {
		
		StringBuffer cadena = new StringBuffer();
		cadena.append("(");
		
		DoubleNode<T> aux = this.last; //setteo el auxiliar en el final
		
		while(aux != null) {

			cadena.append(aux.elem.toString() + " "); //anexo cada elemento llamando a su toString
			aux = aux.prev; //recorro hacia atrás
		}
		
		cadena.append(")");
		return cadena.toString();
		
		
	}

	@Override
	public DoubleList<T> reverse() {

		ArrayList<T> listaInvertida = new ArrayList<T>(); //creo un arrayList para poner los elementos de la lista a la inversa
		
		DoubleNode<T> aux = this.last; //un auxiliar para recorrer la lista del reves
		
		while(aux != null) {
			
			listaInvertida.add(aux.elem); //por cada elemento que voy pasando con el aux lo voy copiando al Arraylist			
			aux = aux.prev; //avanzo hacia atras
		}
		
		DoubleList<T> listaDelReves = new DoubleLinkedListImpl(listaInvertida.toArray(new Object[listaInvertida.size()])); //supongo que el tipo generico T hereda de Object
		
		return listaDelReves;
		
	}


	@Override
	public int maxRepeated() {
			
		int contadorParcial = 0;
		int mayorVeces = 0;
		
		DoubleNode<T> aux = this.front;
		DoubleNode<T> aux2 = this.front;
		
		while(aux != null) { //fijo cada elemento de la lista
			
			contadorParcial = 0; //reseteo las instancias para el nuevo elemento
			
				while(aux2 != null) {   //voy comparandolo con todos y midiendo las instancias
					
					if(aux2.elem.equals(aux.elem)) {
						
						contadorParcial++;
											
					}
					aux2 = aux2.next;
				}
			
			if(contadorParcial > mayorVeces) {

				mayorVeces = contadorParcial;
			
			}
			
			aux = aux.next;			
		}
		
		//mayorVeces tendra el numero mas grande de instancias de un elemento en la lista
		
		return mayorVeces;		
	}


	@Override
	public boolean isEquals(DoubleList<T> other) {
		
		if(other == null) {
			throw new NullPointerException();
		}
		
		
		boolean testigo = true;
			
		if(this.size() != other.size()) { //dos listas de distinto tamaño no tienen los mismo elementos en el mismo orden
			testigo = false;
		}
		else {
		
			for(int posicion = 1; posicion <= other.size(); posicion ++) { //voy a recorrer la lista que me llega elemento a elemento
				
				if(other.getElemPos(posicion).equals(this.getElemPos(posicion)) == false){ //si ya no es igual no tienen los mismo elementos en el mismo orden
					testigo = false;
					break;
				}
			}
		
		}
		return testigo;
		
	}


	@Override
	public boolean containsAll(DoubleList<T> other) {

		if(other == null) {
			throw new NullPointerException();
		}

		boolean testigo = true;

		if(other.size() > this.size()) { //si esta lista es mas pequeña que la que se pasa como parametro no es posible que contenga todos los elementos de other
			testigo = false;
		}
		else {
			
			for(int posicion = 1; posicion <= other.size(); posicion ++) { //recorro la lista que me pasan como parametro porque es mas pequeña o igual
				if(this.contains(other.getElemPos(posicion)) == false) { //voy extrayendo elemento a elemento y comparando con mi lista a ver si contiene cada uno de esos elementos
					testigo = false;
					break;			
				}		
			}
			
		}
		return testigo; //si los ha contenido todos pues mantiene el true, si no, sera false
		
	}


	@Override
	public boolean isSubList(DoubleList<T> other) {

		boolean testigo = false;
		
		if(other == null) {
			throw new NullPointerException();
		}
		
		if(other.isEmpty() == true) {
			testigo = true;
			
		}else if(other.size() > this.size()) {
			testigo = false;
		}else {
				
		
			
			
			DoubleNode<T> aux = this.front;
	
			
			//Recorrere esta lista hasta que el elemento coincida con el primer elemento de other
			
			while(aux != null) {
				
				if(other.getElemPos(1).equals(aux.elem)) { 

					//OJO!! LIMPIAR LISTACOMPROBAR T porque si el primer corte de la lista this (la primera sublista aparentemente coincidente) no corresponde y el segundo si se mantiene lo del primer corte de la lista mas lo del segundo
					DoubleLinkedListImpl<T> listaComprobar;
					listaComprobar = new DoubleLinkedListImpl<T>();
					
					DoubleNode<T> auxListaComprobar = aux;
	
					//corto un trozo de mi lista this del tamaño de other y miro si son exactamente iguales con equals, en cuyo caso será sublista
					for(int i = 1; i <= other.size(); i++) {
						
						if(auxListaComprobar != null) { 
							//por si se da el caso de que sea el ultimo elemento de la lista el que haya coindidido

							listaComprobar.insertLast(auxListaComprobar.elem);

							auxListaComprobar = auxListaComprobar.next;
						}
						
					}
					
					//mando la lista a comprobar
					if(other.isEquals(listaComprobar) == true) {
						//si si que coincide si es sublista
						testigo = true;
						break;
					}
					
					
				}
				aux = aux.next;
				
			}
		}
		
		return testigo;
		
		
		
	}


	@Override
	public String toStringFromUntil(int from, int until) {
		
		StringBuffer cadena = new StringBuffer();


		if(from <= 0 || until <= 0) {
			
			throw new IllegalArgumentException();
		}
		else if(until < from) {
			
			throw new IllegalArgumentException();
			
		}
		else if(from > size()) {

			cadena.append("()");	
		}
		else {
			cadena.append("(");
			DoubleNode<T> aux = this.front;
			
			for(int i = 1; i < from; i++) {
				aux = aux.next;
			}
			//aux esta en from
			for(int i = from; i <= until; i++) { //quiero que pinte de from a until incluidos los dos
				cadena.append(aux.elem.toString() +" ");
				
				if(i == this.size()) {
					break; //para el caso en que el until sobrepase los bounds me adelanto al indexoutofBounds y le mando salir 
				}
				aux = aux.next; //OJO NO OLVIDARMELO!
			}
			cadena.append(")");
		}
		return cadena.toString();
	}
	
	@Override
	public String toString() {
	
		StringBuilder cadena = new StringBuilder();
		
		cadena.append("(");
		
		DoubleNode<T> aux = this.front;

		while(aux != null) {
			
			cadena.append(aux.elem + " ");
			//CUIDADO NO OLVIDARSE EL AUX = AUX.NEXT QUE SE ME CREABA UNA CADENA INFINITA Y ME LLENABA EL HEAP
			aux = aux.next;
		}
		
		cadena.append(")");
		return cadena.toString();		
		
	}

	@Override
	public Iterator<T> iterator() {
		return new DobleLinkedListIterator<T>(this.front);
	}

	@Override
	public Iterator<T> reverseIterator() {
		return new DobleLinkedListreverseIterator<T>(this.last);
		
	}


	@Override
	public Iterator<T> evenPositionsIterator() {
		return new DobleLinkedListevenPositionsIterator<T>(this.front);

	}


	@Override
	public Iterator<T> progressIterator() {
		return new DobleLinkedListprogressIterator<T>(this.front);
	}


}
