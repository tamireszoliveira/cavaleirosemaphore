package controller;
import java.util.concurrent.Semaphore;

public class CavaleiroSemaphore extends Thread {
	private static final int dcorridor = 2000;
	private static final Semaphore semaforo = new Semaphore(1);
	
	private int idperson;
	private int speed;
	private boolean  hastorch, hastone = false;
	private boolean torch, stone = true;
	

	public CavaleiroSemaphore(int idperson) {
		this.idperson = idperson;
		this.speed = (int)(Math.random()* 3)+2;
		this.hastorch = hastorch;
		this.hastone = hastone;
	}
	
	@Override
	public void run() {
		walk();
		door();
	}

	public void walk() {
		int d = 0; //distancia percorrida
		int increment = 2;
		int interval = 50; // intervalo em ms
		
		while( d <500) {
			d += speed;
			
			try {
				Thread.sleep(interval); // simula caminhada
				semaforo.acquire();
				
				if(torch) {
					System.out.println("cavaleiro " + idperson + " pegou a tocha");
					torch = false;
					hastorch = true;
				}
				// ajustando a velocidade se o cavaleiro estiver com a tocha
				if(hastorch) {
					speed += increment;
				}	
				
			} catch(InterruptedException e ){
				e.printStackTrace();
			} finally {
				semaforo.release();
			}
		}
			
	}

		public void stone(boolean hastorch, boolean hastone, boolean torch, boolean stone) {
			int d = 500;
			int increment = 2;
			int interval = 50;
			
			while( d < 1500) {
				d += speed;
				
				try {
					Thread.sleep(interval); // simula caminhada
					semaforo.acquire();
					
					if (hastorch) {
						hastone = false;
					} else {
						if(stone) {
							System.out.println("cavaleiro " + idperson + " pegou a pedra");
							stone = false;
							hastone = true;
					} 
					if(hastone) { 
						speed += increment;
					}
				} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						semaforo.release();
					}
					
			}
		
			}
		private void door() {
			
		}
	}
	
	
		/*try {
			semaforo.acquire();// solicitando permissao pra pegar a tocha
			torch();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforo.release();
		}*/
	