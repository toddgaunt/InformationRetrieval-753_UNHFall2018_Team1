package cs753.T1.A2;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void usage()
	{
		System.out.println("usage: A2 file");
		System.exit(-1);
	}

    public static void main(String[] args)
    {
		String dataFile;

		if (args.length != 1)
			usage();
		dataFile = args[0];
		try {
			FileInputStream fp = new FileInputStream(dataFile);
		} catch (Exception e) {
			System.out.println("Please specify an input file that exists");
		}
    }
}
