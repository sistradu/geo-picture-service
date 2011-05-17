package de.hszigr.gpics.util;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 27.04.11
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class CoordinateCalculator {

    /**
	 * @param position
     * @param ref gibt an ob N, S, O, W
	 * @return
	 */
	public String getDecimalCoordinate(String position, String ref) {
        try {
            double degree = Double.parseDouble(position.substring(0, position.indexOf("\"")));
            double minutes = Double.parseDouble(position.substring(position.indexOf("\"")+1, position.indexOf("'")));
            double seconds = Double.parseDouble(position.substring(position.indexOf("'")+1));
            degree += minutes/60;
            degree += seconds/3600;
            if(ref.equals("S")||ref.equals("W")){
                degree *= -1;
            }
            return String.valueOf(degree);
        } catch (NumberFormatException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NullPointerException e){
            System.err.println("Position or Positionref is null.");
        } catch(StringIndexOutOfBoundsException e){
            System.err.println("Position or Positionref is null.");
        }
        return "";
    }
}
