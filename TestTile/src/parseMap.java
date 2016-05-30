import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class parseMap {
    private int widthMap;
    private int heightMap;
    private int widthTile;
    private int heightTile;
    private String nameFile = "./img/map.txt";

    public ArrayList<Point> getTabSpawnWall() {return tabSpawnWall;}

    private ArrayList<Point> tabSpawnWall;


    public parseMap() throws ParserConfigurationException
    {

        try
        {
            tabSpawnWall = new ArrayList<Point>();

            BufferedReader buff = initMapParser("./img/map.txt");

            String ligne;
            while ((ligne=buff.readLine())!=null)
            {
                if(ligne.contains("[header]"))
                {
                    ligne=buff.readLine();                  //increment de la ligne
                    widthMap = Integer.parseInt(ligne.substring(6));

                    ligne=buff.readLine();                  //increment de la ligne
                    heightMap = Integer.parseInt(ligne.substring(7));

                    ligne=buff.readLine();                 // increment ligne
                    widthTile = Integer.parseInt(ligne.substring(10));

                    ligne=buff.readLine();
                    heightTile = Integer.parseInt(ligne.substring(11));

                    System.out.println(widthMap + " " + widthTile + " " + heightMap + " " + heightTile);
                }
                buff.close();

                // Lecture du fichier map pour récupération des positions des murs
                BufferedReader buffCollision = initMapParser(nameFile);
                computeTiledElements(buffCollision, "collision", tabSpawnWall);
                buffCollision.close();


            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }


    /*
    *   Lecture du fichier pour définition de la taille de la map et du placement des différents éléments.
    *
     */
    private BufferedReader initMapParser(String fileName) throws FileNotFoundException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        InputStream flux= new FileInputStream(file);
        InputStreamReader lecture=new InputStreamReader(flux);
        BufferedReader buff = new BufferedReader(lecture);

        return buff;
    }

    /*
    *   Remplissage d'un tableau de position en fonction d'un nom de layer sur un fichier map.
    *
     */
    private void computeTiledElements(BufferedReader buff, String layerName, ArrayList<Point> list) throws IOException
    {
        String ligne;
        while ((ligne = buff.readLine())!=null)
        {
            if(ligne.contains("[layer]"))
            {
                ligne=buff.readLine(); //increment de la ligne
                if (ligne.contains("type=" + layerName))
                {
                    ligne = buff.readLine(); // passer à la ligne data
                    ligne = buff.readLine(); // passer au début de la matrice


                    int row = 0;
                    while (row <= heightMap)
                    {
                        int column = 0;
                        StringTokenizer newLigne = new StringTokenizer(ligne, ",");
                        while (newLigne.hasMoreElements())
                        {
                            if (Integer.parseInt(newLigne.nextElement().toString()) != 0)
                            {
                                Point tmpP = new Point(column * widthTile, row * heightTile);
                                list.add(tmpP);
                            }
                            column++;
                        }
                        row++;
                        ligne = buff.readLine();
                    }
                }
            }
        }


    }


}
