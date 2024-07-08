package com.project.reelRadar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReelRadarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReelRadarApplication.class, args);


		/** Testing Docker **/
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(5000);
					System.out.println("live");
					System.out.println("####");
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.err.println("Thread was interrupted, Failed to complete operation");
				}
			}
		}).start();
	}
}
