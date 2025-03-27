package controller;
import java.util.concurrent.Semaphore;
/* 4 cavaleiros caminham por um corredor, simultaneamente, de 2 a 4 m por 50 ms. O corredor é
escuro, tem 2 km e em 500 m, há uma única tocha. O cavaleiro que pegar a tocha, aumenta sua
velocidade, somando mais 2 m por 50 ms ao valor que já fazia. Em 1,5 km, existe uma pedra
brilhante. O cavaleiro que pegar a pedra, aumenta sua velocidade, somando mais 2 m por 50 ms
ao valor que já fazia (O cavaleiro que já detém a tocha não poderá pegar a pedra). Ao final dos 2
km, os cavaleiros se deparam com 4 portas e, um por vez pega uma porta aleatória (que não pode
repetir) e entra nela. Apenas uma porta leva à saída. As outras 3 tem monstros que os devoram.*/

public class CavaleiroSemaphore extends Thread {
	private static final int dcorridor = 2000;
	private static final Semaphore semaforo = new Semaphore(1);
	
	private int idperson;
	private int speed;
	private boolean  hastorch, hastone = false;
	private boolean torch, stone = true;
	private int d;

	public CavaleiroSemaphore(int idperson) {
		this.idperson = idperson;
		this.speed = (int)(Math.random()* 3)+2;
		this.hastorch = hastorch;
		this.hastone = hastone;
	}
	
	@Override
	public void run() {
		walk();
		door(d);
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
				}
			}
				 catch (InterruptedException e) {
						e.printStackTrace();
				} finally {
						semaforo.release();
				}
					
			}
		
		}
		private void door(int d) {
			boolean door1 = false; // porta 1 fechada - contém a saída
			/*boolean door2 = false; // porta 2 fechada
			boolean door3 = false; // porta 3 fechada
			boolean door4 = false; // porta 4 fechada*/
			int interval = 50;
			while(d < 2000) {
				d+= speed;
				try {
					Thread.sleep(interval); // simula caminhada
					
					
					if (door1 == false) {
						semaforo.acquire();
						door1 = true;
						System.out.println("O cavaleiro " + idperson + " achou a saída. Os demais, morreram.");
					
						
					}
				} catch (InterruptedException e){
						e.printStackTrace();
				} finally {
						semaforo.release();
				}
			}
		}

	}
