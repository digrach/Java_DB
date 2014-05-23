package cabbage.database.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import cabbage.database.utilities.DataBaseConnection;

////// JUST PLAYIN
public class MapOps {
	
	private DataBaseConnection db = new DataBaseConnection();

	Map<Integer,Map<Integer,Map<Integer,Integer>>> zMap;

	public MapOps() {

	}
	
//	private Connection getConnection() {
//
//		Context ctx;
//		Connection connection = null;
//		try {
//			ctx = new InitialContext();
//			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/cabbagedb");
//			connection = ds.getConnection();
//		} catch (NamingException e) {
//			throw new RuntimeException(e);
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//
//		return connection;
//
//	}

	public Map<Integer, Map<Integer, Map<Integer, Integer>>> getMapColorPixels(int level) throws SQLException {
		getMapRecords(level);
		return zMap;
	}
	
//	public int[] getArrayColorPixels(int level) throws SQLException {
	public String[] getArrayColorPixels(int level) throws SQLException {
		getMapRecords(level);
		return buildPixelArray(level);
	}

	private void getMapRecords(int level) throws SQLException {

		Connection conn = db.getConnection();
		String sqlString = "SELECT z,y,x,cellcategory_id FROM location WHERE z=?";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ps.setInt(1, level);
		ResultSet rs = ps.executeQuery();

		zMap = new TreeMap<Integer,Map<Integer,Map<Integer,Integer>>>();

		while(rs.next()) {
			int z = rs.getInt("z");
			int y = rs.getInt("y");
			int x = rs.getInt("x");
			int cellcat = rs.getInt("cellcategory_id");
			addEntry(z,y,x,cellcat);
		}

	}



	private boolean addEntry(int z, int y, int x, int cellcat) {

		if (! zMap.containsKey(z)) {
			zMap.put(z, new TreeMap<Integer,Map<Integer,Integer>>());
			zMap.get(z).put(y,new TreeMap<Integer,Integer>());
			zMap.get(z).get(y).put(x,cellcat);
			return true;
		} else {
			if (! zMap.get(z).containsKey(y)) {
				zMap.get(z).put(y,new TreeMap<Integer,Integer>());
				zMap.get(z).get(y).put(x,cellcat);
				return true;
			} else {
				if (! zMap.get(z).get(y).containsKey(x)) {
					zMap.get(z).get(y).put(x,cellcat);
					return true;
				} 
			}
		}

		return false;
	}
	
//	private int[] buildPixelArray(int zLevel) throws SQLException {
	private String[] buildPixelArray(int zLevel) throws SQLException {
		 
		int[] CategoryColors  = {0xFF000000,0xFFB0171F,0xFFFF3E96,0xFF0000FF,0xFF00BFFF,0xFF00C957};
		String[] CategoryColors2  = {"deeppink","darkturquoise","cornflowerblue","aquamarine","greenyellow","honeydew"};
		
		List<Integer> cellCategories = getCellCategories();
		int[] extents = getLevelExtents(zLevel);
		int widthX = extents[0];
		int heightY = extents[1];
		int firstXKey = extents[2];
		int lastXKey = extents[3];
		int firstYKey = extents[4];
		int lastYKey = extents[5];

//		int[] pixelList = new int[88 * 153];
		String[] pixelList = new String[88 * 153];
		
		 Map<Integer,Map<Integer,Integer>> yMap = zMap.get(zLevel);
		 

		//int zKeys[] = wg.getAllZKeys();

		//cabbage.utilities.GridCellTypeEnum.GridCellCategory[] cellCategories = cabbage.utilities.GridCellTypeEnum.GridCellCategory.values();
		int bitmapArray[][] = new int[88][153];
		for (Map.Entry<Integer, Map<Integer,Integer>> yEntry : yMap.entrySet()) {
			int yKey = yEntry.getKey();
			Map<Integer,Integer> yValue = yEntry.getValue();
			for (Map.Entry<Integer,Integer> xEntry : yValue.entrySet()) {
				int xKey = xEntry.getKey();
				int cellcat = xEntry.getValue();
				for (int k = 0; k < cellCategories.size(); k++) {
					if (cellcat == cellCategories.get(k)) {
//						pixelList[((yKey-1)*widthX) + (xKey-1)] = CategoryColors[k];
						pixelList[((yKey-1)*widthX) + (xKey-1)] = CategoryColors2[k];
					}
//					if (cellcat.getCellCategory().equals(cellCategories[k].toString())) {
//						pixelList[((yKey-1)*widthX) + (xKey-1)] = (Integer)GridCellTypeEnum.CategoryColors[k];
//					}
				}
//				for (int k = 0; k < cellCategories.length; k++) {
//					if (cellcat.getCellCategory().equals(cellCategories[k].toString())) {
//						pixelList[((yKey-1)*widthX) + (xKey-1)] = (Integer)GridCellTypeEnum.CategoryColors[k];
//					}
////					if (cellcat.getCellCategory().equals(cellCategories[k].toString())) {
////						pixelList[((yKey-1)*widthX) + (xKey-1)] = (Integer)GridCellTypeEnum.CategoryColors[k];
////					}
//				}
			}
		}

		return pixelList;
	}
	
	private int[] getLevelExtents(int zKey) {
		int[] levelAreaSize = null;
		if (zMap.containsKey(zKey)) {
			// Get the yMap at the zKey
			NavigableMap<Integer,Map<Integer,Integer>> yMap = (NavigableMap<Integer,Map<Integer,Integer>>)zMap.get(zKey);
			// Get the first key of y
			int firstYKey = yMap.firstKey();
			// Get the last key of y
			int lastYKey = yMap.lastKey();

			int firstXKey = 153; // Should be world max
			int lastXKey = 1; // Should be world min
			// Loop through each xMap in yMap.
			for (Map.Entry<Integer,Map<Integer,Integer>> yEntry : yMap.entrySet()) {
				// Get the current xMap.
				NavigableMap<Integer,Integer> xMap = (NavigableMap<Integer, Integer>)yEntry.getValue();
				// Get the firstKey
				if (xMap.firstKey() < firstXKey) {
					firstXKey = xMap.firstKey();
				}
				// Get the lastKey.
				if (xMap.lastKey() > lastXKey) {
					lastXKey = xMap.lastKey();
				}

			}

			int yHeight = lastYKey - firstYKey + 1;
			int xWidth = lastXKey - firstXKey + 1;

			levelAreaSize = new int[6];
			levelAreaSize[0] = xWidth;
			levelAreaSize[1] = yHeight;
			levelAreaSize[2] = firstXKey;
			levelAreaSize[3] = lastXKey;
			levelAreaSize[4] = firstYKey;
			levelAreaSize[5] = lastYKey;

		}
		return levelAreaSize;
	}
	
	private List<Integer> getCellCategories() throws SQLException {

		Connection conn = db.getConnection();
		String sqlString = "SELECT cellcategory_id FROM cellcategory";
		PreparedStatement ps = conn.prepareStatement(sqlString);
		ResultSet rs = ps.executeQuery();


		List<Integer> ccList = new ArrayList<Integer>();
		while(rs.next()) {
			ccList.add(rs.getInt("cellcategory_id"));
		}
		
		return ccList;

	}
	
	public List<String> makeSVGArray(int level) throws SQLException {

		int width = 153;
		int height = 88;

		int circleSize = 3;
		int strokeWidth = 1;

		int xCoord = circleSize + strokeWidth;
		int yCoord = circleSize + strokeWidth;

		int advanceincrement = (circleSize + strokeWidth)*2;

		//MapOps mo = new MapOps();
//		String[] pList = mo.getArrayColorPixels(0);
		String[] pList = getArrayColorPixels(0);
		List<String> svgStrings = new ArrayList<String>();

		int totalCount = 0;
		int innerCount = 0;
		int count = 0;

		String unmappedColor = "Black";

		while ( count < height ) {

			svgStrings.add("<text x=\"" + xCoord + "\" y=\"" + yCoord + "\" font-size=\"" + circleSize + "px\" fill=\"red\">" + count + "</text>");
			xCoord += advanceincrement;
			innerCount = 0;

			while (innerCount < width ) {

				if ( count < 1) {
					svgStrings.add("<text x=\"" + xCoord + "\" y=\"" + yCoord + "\" font-size=\"" + circleSize + "px\" fill=\"red\">" + (innerCount+1) + "</text>");
					xCoord += advanceincrement;
					innerCount ++;
				} else {
					if (pList[totalCount] == null) {
						svgStrings.add("<circle cx=\"" + xCoord + "\"cy=\"" + yCoord + "\"r=\"" + circleSize + "\" stroke=\"black\" stroke-width=\"" + strokeWidth + "\" fill=\"" + unmappedColor + "\" />");
						xCoord += advanceincrement;
						innerCount ++;
						totalCount ++; 
					} else {
						svgStrings.add("<circle cx=\"" + xCoord + "\"cy=\"" + yCoord + "\"r=\"" + circleSize + "\" stroke=\"black\" stroke-width=\"" + strokeWidth + "\" fill=\"" + pList[totalCount] + "\" />");
						xCoord += advanceincrement;
						innerCount ++;
						totalCount ++; 
					}
				}

			}

			xCoord = circleSize + strokeWidth;
			yCoord += advanceincrement;
			count ++;

		}
		return svgStrings;
	}



}



//package cabbage.database.operations;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.NavigableMap;
//import java.util.TreeMap;
//
//
//
//
//import cabbage.database.utilities.DataBaseConnection;
//
//public class MapOps {
//
//	private DataBaseConnection db;
//	Map<Integer,Map<Integer,Map<Integer,Integer>>> zMap;
//
//	public MapOps() {
//
//	}
//
//	public Map<Integer, Map<Integer, Map<Integer, Integer>>> getMapColorPixels(int level) throws SQLException {
//		getMapRecords(level);
//		return zMap;
//	}
//	
////	public int[] getArrayColorPixels(int level) throws SQLException {
//	public String[] getArrayColorPixels(int level) throws SQLException {
//		getMapRecords(level);
//		return buildPixelArray(level);
//	}
//
//	private void getMapRecords(int level) throws SQLException {
//
//		db = new DataBaseConnection();
//		Connection conn = db.getConnection();
//		String sqlString = "SELECT z,y,x,cellcategory_id FROM location WHERE z=?";
//		PreparedStatement ps = conn.prepareStatement(sqlString);
//		ps.setInt(1, level);
//		ResultSet rs = ps.executeQuery();
//
//		zMap = new TreeMap<Integer,Map<Integer,Map<Integer,Integer>>>();
//
//		while(rs.next()) {
//			int z = rs.getInt("z");
//			int y = rs.getInt("y");
//			int x = rs.getInt("x");
//			int cellcat = rs.getInt("cellcategory_id");
//			addEntry(z,y,x,cellcat);
//		}
//
//	}
//
//
//
//	private boolean addEntry(int z, int y, int x, int cellcat) {
//
//		if (! zMap.containsKey(z)) {
//			zMap.put(z, new TreeMap<Integer,Map<Integer,Integer>>());
//			zMap.get(z).put(y,new TreeMap<Integer,Integer>());
//			zMap.get(z).get(y).put(x,cellcat);
//			return true;
//		} else {
//			if (! zMap.get(z).containsKey(y)) {
//				zMap.get(z).put(y,new TreeMap<Integer,Integer>());
//				zMap.get(z).get(y).put(x,cellcat);
//				return true;
//			} else {
//				if (! zMap.get(z).get(y).containsKey(x)) {
//					zMap.get(z).get(y).put(x,cellcat);
//					return true;
//				} 
//			}
//		}
//
//		return false;
//	}
//	
////	private int[] buildPixelArray(int zLevel) throws SQLException {
//	private String[] buildPixelArray(int zLevel) throws SQLException {
//		 
//		int[] CategoryColors  = {0xFF000000,0xFFB0171F,0xFFFF3E96,0xFF0000FF,0xFF00BFFF,0xFF00C957};
//		String[] CategoryColors2  = {"deeppink","darkturquoise","cornflowerblue","aquamarine","greenyellow","honeydew"};
//		
//		List<Integer> cellCategories = getCellCategories();
//		int[] extents = getLevelExtents(zLevel);
//		int widthX = extents[0];
//		int heightY = extents[1];
//		int firstXKey = extents[2];
//		int lastXKey = extents[3];
//		int firstYKey = extents[4];
//		int lastYKey = extents[5];
//
////		int[] pixelList = new int[88 * 153];
//		String[] pixelList = new String[88 * 153];
//		
//		 Map<Integer,Map<Integer,Integer>> yMap = zMap.get(zLevel);
//		 
//
//		//int zKeys[] = wg.getAllZKeys();
//
//		//cabbage.utilities.GridCellTypeEnum.GridCellCategory[] cellCategories = cabbage.utilities.GridCellTypeEnum.GridCellCategory.values();
//		int bitmapArray[][] = new int[88][153];
//		for (Map.Entry<Integer, Map<Integer,Integer>> yEntry : yMap.entrySet()) {
//			int yKey = yEntry.getKey();
//			Map<Integer,Integer> yValue = yEntry.getValue();
//			for (Map.Entry<Integer,Integer> xEntry : yValue.entrySet()) {
//				int xKey = xEntry.getKey();
//				int cellcat = xEntry.getValue();
//				for (int k = 0; k < cellCategories.size(); k++) {
//					if (cellcat == cellCategories.get(k)) {
////						pixelList[((yKey-1)*widthX) + (xKey-1)] = CategoryColors[k];
//						pixelList[((yKey-1)*widthX) + (xKey-1)] = CategoryColors2[k];
//					}
////					if (cellcat.getCellCategory().equals(cellCategories[k].toString())) {
////						pixelList[((yKey-1)*widthX) + (xKey-1)] = (Integer)GridCellTypeEnum.CategoryColors[k];
////					}
//				}
////				for (int k = 0; k < cellCategories.length; k++) {
////					if (cellcat.getCellCategory().equals(cellCategories[k].toString())) {
////						pixelList[((yKey-1)*widthX) + (xKey-1)] = (Integer)GridCellTypeEnum.CategoryColors[k];
////					}
//////					if (cellcat.getCellCategory().equals(cellCategories[k].toString())) {
//////						pixelList[((yKey-1)*widthX) + (xKey-1)] = (Integer)GridCellTypeEnum.CategoryColors[k];
//////					}
////				}
//			}
//		}
//
//		return pixelList;
//	}
//	
//	private int[] getLevelExtents(int zKey) {
//		int[] levelAreaSize = null;
//		if (zMap.containsKey(zKey)) {
//			// Get the yMap at the zKey
//			NavigableMap<Integer,Map<Integer,Integer>> yMap = (NavigableMap<Integer,Map<Integer,Integer>>)zMap.get(zKey);
//			// Get the first key of y
//			int firstYKey = yMap.firstKey();
//			// Get the last key of y
//			int lastYKey = yMap.lastKey();
//
//			int firstXKey = 153; // Should be world max
//			int lastXKey = 1; // Should be world min
//			// Loop through each xMap in yMap.
//			for (Map.Entry<Integer,Map<Integer,Integer>> yEntry : yMap.entrySet()) {
//				// Get the current xMap.
//				NavigableMap<Integer,Integer> xMap = (NavigableMap<Integer, Integer>)yEntry.getValue();
//				// Get the firstKey
//				if (xMap.firstKey() < firstXKey) {
//					firstXKey = xMap.firstKey();
//				}
//				// Get the lastKey.
//				if (xMap.lastKey() > lastXKey) {
//					lastXKey = xMap.lastKey();
//				}
//
//			}
//
//			int yHeight = lastYKey - firstYKey + 1;
//			int xWidth = lastXKey - firstXKey + 1;
//
//			levelAreaSize = new int[6];
//			levelAreaSize[0] = xWidth;
//			levelAreaSize[1] = yHeight;
//			levelAreaSize[2] = firstXKey;
//			levelAreaSize[3] = lastXKey;
//			levelAreaSize[4] = firstYKey;
//			levelAreaSize[5] = lastYKey;
//
//		}
//		return levelAreaSize;
//	}
//	
//	private List<Integer> getCellCategories() throws SQLException {
//
//		db = new DataBaseConnection();
//		Connection conn = db.getConnection();
//		String sqlString = "SELECT cellcategory_id FROM cellcategory";
//		PreparedStatement ps = conn.prepareStatement(sqlString);
//		ResultSet rs = ps.executeQuery();
//
//
//		List<Integer> ccList = new ArrayList<Integer>();
//		while(rs.next()) {
//			ccList.add(rs.getInt("cellcategory_id"));
//		}
//		
//		return ccList;
//
//	}
//
//
//}
