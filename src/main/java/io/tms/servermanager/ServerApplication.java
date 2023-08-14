package io.tms.servermanager;

import io.tms.servermanager.enumeration.Status;
import io.tms.servermanager.model.Server;
import io.tms.servermanager.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepo serverRepo) {
		return args -> {
			serverRepo.save(new Server(
					1L,
					"192.168.0.1",
					"Fedora Linux",
					"16 GB",
					"Personal PC",
					"http://localhost:8080/server/image/server1.png",
					Status.SERVER_UP
			));
			serverRepo.save(new Server(
                    2L,
					"192.168.0.2",
					"Ubuntu Linux",
					"32 GB",
					"Work PC",
					"http://localhost:8080/server/image/server2.png",
					Status.SERVER_DOWN
			));
			serverRepo.save(new Server(
					3L,
					"192.168.0.3",
					"Windows Server",
					"64 GB",
					"Office PC",
					"http://localhost:8080/server/image/server3.png",
					Status.SERVER_UP
			));
			serverRepo.save(new Server(
					4L,
					"192.168.0.4",
					"Mac OS Server",
					"8 GB",
					"Home PC",
					"http://localhost:8080/server/image/server4.png",
					Status.SERVER_DOWN
			));
		};
	}
}
