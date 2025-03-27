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
	private static final Semaphore semaforotorch = new Semaphore(1);
	private static final Semaphore semaforostone = new Semaphore(1);
	private static final Semaphore semaforoexit = new Semaphore(1);
	
	private int idperson;
	private int speed;
	private boolean  hastorch, hastone = false;
	private int d = 0; //distancia percorrida pelo cavaleiro

	public CavaleiroSemaphore(int idperson) {
		this.idperson = idperson;
		this.speed = (int)(Math.random()* 3)+2;
		}
	
	@Override
	public void run() {
		while(d < dcorridor) {
			walk();
		}
		door(d);
	}

	public void walk() {
		
		int interval = 50; // intervalo em ms
		d += speed;
		
		System.out.println("Cavaleiro" + idperson + " andou " + d + " metros.");
		
		try {
			Thread.sleep(interval); // simula caminhada
			if(d >= 500 && d < 1500 ) {
				semaforotorch.acquire();
				if(!hastorch) {
					System.out.println("cavaleiro " + idperson + " pegou a tocha");
					hastorch = true;
					speed +=2;
				}
				semaforotorch.release();
			}
			
			if (d >= 1500 && d < dcorridor) {
				semaforostone.acquire();
				if(!hastorch && !hastone) {
					System.out.println("cavaleiro " + idperson + " pegou a pedra");
					hastone = true;
					speed +=2;
				}
				semaforostone.release();
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	
	
	private void door(int d) {
		boolean door1 = false; // porta 1 fechada - contém a saída
		/*boolean door2 = false; // porta 2 fechada
		boolean door3 = false; // porta 3 fechada
		boolean door4 = false; // porta 4 fechada*/
	
		try {
			semaforoexit.acquire();
				System.out.println("O cavaleiro " + idperson + " achou a saída. Os demais, morreram.");
		} catch (InterruptedException e){
				e.printStackTrace();
		} finally {
				semaforoexit.release();
		}
	}
}

	
