package clases;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		WarriorContainer warriorList = new WarriorContainer();

		System.out.println(warriorList);

		Random rand = new Random();
		
		Player cpu = new Player(warriorList.getWarriors().get(rand.nextInt(warriorList.getWarriors().size())));
		Player usr = new Player(warriorList.getWarriors().get(rand.nextInt(warriorList.getWarriors().size())));
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(usr);
		playerList.add(cpu);
		
		Scanner sc = new Scanner(System.in);
		
		boolean play = true;
		
		int opt = 0;
		
		while (play) {
			System.out.println("Bienvenido:\n1-Jugar\n2-Salir");
			if (sc.hasNextInt()) {
				opt = sc.nextInt();
				sc.nextLine();
				
				switch (opt) {
				case 1: {
					cpu.setCurrentHP(cpu.getWarrior().getHp());
					usr.setCurrentHP(usr.getWarrior().getHp());
					
					System.out.println("Empieza el combate entre:\n" + usr.getWarrior().getName() + "\n" + cpu.getWarrior().getName());
					System.out.println("\nPULSA ENTER PARA EMPEZAR");
					
					int i = 0;
					
					while (cpu.getCurrentHP() > 0 & usr.getCurrentHP() > 0) {
						
						playerList.get(i).atack(playerList.get((i + 1)%2));
						
						i = (i + 1) % 2;
						
						System.out.println("Vida CPU = " + cpu.getCurrentHP());
						System.out.println("Vida USR = " + usr.getCurrentHP());
						
						System.out.println("ENTER PARA CONTINUAR");
						
						sc.nextLine();
						
					}
					
					System.out.println("Fin de partida:\n1-Seguir Jugandon\n2-Salir");
					while (true) {
						if (sc.hasNextInt()) {
							opt = sc.nextInt();
							break;
						}
						System.out.println("Error");
					}
						
					switch (opt) {
					case 1: {
						if (usr.getCurrentHP() > 0) {
							cpu.setWarrior(warriorList.getWarriors().get(rand.nextInt(3)));
							cpu.setWeapon(cpu.getWarrior().getWeapons().get(rand.nextInt(3)));
						}
						else {
							cpu.setWarrior(warriorList.getWarriors().get(rand.nextInt(3)));
							cpu.setWeapon(cpu.getWarrior().getWeapons().get(rand.nextInt(3)));
							
							usr.setWeapon(usr.getWarrior().getWeapons().get(rand.nextInt(3)));
						}
						break;
					}
					case 2: {
						System.out.println("Se han subido tus datos ;)\nCerrando juego");
						play = false;
						break;
					}
					default:
						System.out.println("Opcion fuera de rango");
					}
					
					break;
				}
				case 2: {
					System.out.println("Cerrando programa");
					play = false;
					break;
				}
				default:
					System.out.println("Fuera de rango");;
				}
				
			}
			else System.out.println("Error");
			
		}
		
	}

}
