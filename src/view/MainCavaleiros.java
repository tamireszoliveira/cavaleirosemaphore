package view;
import controller.CavaleiroSemaphore;

public class MainCavaleiros {

	public static void main(String[] args) {
		for(int i = 0; i < 4; i++) {
			CavaleiroSemaphore cavaleiro = new CavaleiroSemaphore(i);
			cavaleiro.start();
		}
	}

}
