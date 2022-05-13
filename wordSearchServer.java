/**
 * Alisha Hannan
 * COP2805C
 * Word Searcher Server Side
 */

package cop2805;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.nio.file.*;

public class wordSearchServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		boolean shutdown = false;
		try {
			server = new ServerSocket(1236);
			System.out.println("Port bound. Accepting Conenctions");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		while (!shutdown) {
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;

			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();

				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);

				String clientInput = new String(data, StandardCharsets.UTF_8);
				clientInput.replace("\n", "");

				List<Integer> response = new ArrayList<>();
				response = wordSearcher.findWord(clientInput);

				output.write(response.size());
				for (int x = 0; x < response.size(); x++) {
					String line = response.get(x).toString() + "\n";
					output.write(line.getBytes());
				}

				client.close();
				if (clientInput.equalsIgnoreCase("shutdown")) {
					System.out.println("Shutting down...");
					shutdown = true;
				}

			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}

class wordSearcher {
	static List<String> lines = new ArrayList<>();
	static Path file = Paths.get("C:/temp/hamlet.txt");

	public wordSearcher() {
		try {
			lines = Files.readAllLines(file, StandardCharsets.UTF_8);
			lines.replaceAll(String::toUpperCase);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static List<Integer> findWord(String searchString) {
		List<Integer> returnList = new ArrayList<>();
		searchString = searchString.toUpperCase();
		for (int i = 0; i < lines.size(); i++) {
			String str = lines.get(i);
			if (str.indexOf(searchString) >= 0) {
				returnList.add(i);
			}
		}
		return returnList;
	}
}