package clases;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}

		//Instance the warriors container
		WarriorContainer warriorList = new WarriorContainer();

		// Instance the players
		Player cpu = new Player(warriorList.getRandomWarrior(), "CPU");
		Player usr = new Player(warriorList.getRandomWarrior(), "USR");
		cpu.setWeapon();
		new GUI(usr, cpu, warriorList);
		/*
		// Instance the ArrayList to put both player and manage the turns
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
					boolean battle = true;
					RoundsInfo pruebaSubida = new RoundsInfo(cpu, usr);

					while (battle) {
						cpu.setCurrentHP(cpu.getWarrior().getHp());
						usr.setCurrentHP(usr.getWarrior().getHp());

						System.out.println("Empieza el combate entre:\n" + usr + "\n" + cpu);
						System.out.println("\nPULSA ENTER PARA EMPEZAR");

						int i = 0;

						// Loop of the battle
						while (cpu.getCurrentHP() > 0 & usr.getCurrentHP() > 0) {

							playerList.get(i).atack(playerList.get((i + 1) % 2));

							i = (i + 1) % 2;

							System.out.println("Health Points " + usr.getName() + " = " + usr.getCurrentHP());
							System.out.println("Health Points " + cpu.getName() + " = " + cpu.getCurrentHP());

							System.out.println("ENTER PARA CONTINUAR");

							sc.nextLine();

						}

						if (usr.getCurrentHP() > 0) {
							pruebaSubida.sumInjuriesCaused(cpu.getWarrior().getHp() - cpu.getCurrentHP());
							pruebaSubida.sumInjuriesSuffered(usr.getWarrior().getHp() - usr.getCurrentHP());

							pruebaSubida.updateData(cpu.getWarrior().getDefeatPoints() + cpu.getWeapon().getDefeatPoints());

						}
						else{
							pruebaSubida.sumInjuriesCaused(cpu.getWarrior().getHp() - cpu.getCurrentHP());
							pruebaSubida.sumInjuriesSuffered(usr.getWarrior().getHp() - usr.getCurrentHP());

							pruebaSubida.updateData();

						}

						// TODO Cambiar el final de partida
						System.out.println("Fin de partida:\n1-Seguir Jugandon\n2-Salir");
						while (true) {
							if (sc.hasNextInt()) {
								opt = sc.nextInt();
								break;
							}
							System.out.println("Error");
						}

						switch (opt) {// PopUp
							case 1: {
								if (usr.getCurrentHP() > 0) {
									cpu.setWarrior(warriorList.getRandomWarrior());
									cpu.setWeapon();

									pruebaSubida.setIdOpponent(cpu.getWarrior().getIdWarrior());
									pruebaSubida.setIdOpponentWeapon(cpu.getWeapon().getIdWeapon());

								} else {
									cpu.setWarrior(warriorList.getRandomWarrior());
									cpu.setWeapon();

									usr.setWeapon();

									battle = false;
								}
								break;
							}
							case 2: {
								System.out.println("Se han subido tus datos ;)");
								battle = false;
								break;
							}
							default:
								System.out.println("Opcion fuera de rango");
						}

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

		 */
		
	}



}
