/*
* Programmation concurrente
* 
* Tableau de garde action :
* 
* Methode | Garde         | Action
* -------------------------------------
* get()   | ressource > 0 | ressource--
* put()   | /             | ressource++
*/

/* Solution directe : */

class Allocate{
	int ressource;

	public synchronized get(){
		while(!(ressource > 0)){
			wait();
		}
		ressource--;
	}

	public synchronized put(){
		ressource++;
		notifyAll();
	}
}

/* Solution semaphore fifo : */

Semaphore fifo = new Semaphore(1);

class Allocate{
	int ressource;

	public get(){
		fifo.aquire();
		synchronized (this) {
			while(!(ressource > 0)){
				wait();
			}
			ressource--;
		}
		fifo.release();
	}

	public synchronized put(){
		ressource++;
		notifyAll();
	}
}