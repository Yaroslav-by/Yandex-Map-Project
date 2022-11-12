package yandexAPI;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapObject {

	private final static String APIKEY = Apikey.getAPIKEY();
	private final static String TYPE_GEO = "type=geo";
	private final static String TYPE_BIZ = "type=biz";
	private final static String LANG = "ru_RU";
	
	private String coordinates;
	private String name;
	private String description;
	
	private String request;
	
	MapObject(String coordinates, String name, String description) {
		this.coordinates = coordinates;
		this.name = name;
		this.description = description;
	}
	
	public String getCoordinates() {
		return coordinates;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public String getRequest() {
		return request;
	}

	public static String getResults(String query, boolean isGEO) {
		
		String queryType;
		if (isGEO)
			queryType = TYPE_GEO;
		else 
			queryType = TYPE_BIZ;
			
		while (query.contains(" ")) {
			query = query.replaceAll(" ", "+");
		}
		
		try {
			
			HttpRequest getRequest = HttpRequest.newBuilder()
					.uri(new URI("https://search-maps.yandex.ru/v1/"
							+ "?text=" + query
							+ "&" + queryType
							+ "&lang=" + LANG
							+ "&apikey=" + APIKEY))
					.GET()
					.build();
			
			HttpClient client = HttpClient.newHttpClient();
			
			HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
			
			return getResponse.body();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static ArrayList<MapObject> getMapObjects(String jsonGEO, String jsonBIZ) {
		
		ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();
		
		if (jsonGEO.equals(null) && jsonBIZ.equals(null)) {
			return mapObjects;
		}
		
		JSONObject jsonObject = new JSONObject(jsonGEO);
		JSONArray jsonArray = (JSONArray) jsonObject.get("features");
		
		for (Object i : jsonArray) {
			
			String coordinates = (String) ((JSONObject) i).getJSONObject("geometry")
														  .get("coordinates")
														  .toString();
			coordinates = coordinates.substring(1, coordinates.length() - 1);
			
			String name = (String) ((JSONObject) i).getJSONObject("properties").get("name");
			
			String description = "��������: " + name + "\n";
			
			try {
				description = description + "�����: "  
						+ (String) ((JSONObject) i).getJSONObject("properties").get("description") + "\n";
			} catch (JSONException e) {
				description = description + "�����: �� ���������";
			}
			
			description = description + "����������: " + coordinates;
			
			MapObject temp = new MapObject(coordinates, name, description);
			mapObjects.add(temp);
		}
		
		jsonObject = new JSONObject(jsonBIZ);
		jsonArray = (JSONArray) jsonObject.getJSONArray("features");
		
		for (Object i : jsonArray) {
			
			String coordinates = (String) ((JSONObject) i).getJSONObject("geometry")
														  .get("coordinates")
														  .toString();
			coordinates = coordinates.substring(1, coordinates.length() - 1);
			
			String name = (String) ((JSONObject) i).getJSONObject("properties").getString("name") + " (�����������)";
			
			String description = "��������: " + (String) ((JSONObject) i).getJSONObject("properties").getString("name") + "\n";
			
			//���������� ����� � ��������
			try {
				description = description + "�����: " + (String) ((JSONObject) i).getJSONObject("properties")
																				 .getJSONObject("CompanyMetaData")
																				 .get("address") + "\n";
			} catch (JSONException e) {
				description = description + "�����: �� ���������\n";
			}
			
			//���������� ������ ��������� � �������� 
			try {
				JSONArray phonesArray = ((JSONObject) i).getJSONObject("properties")
													    .getJSONObject("CompanyMetaData")
													    .getJSONArray("Phones");
				description = description + "���. �������: ";
				for (Object j : phonesArray) {
					description = description + (String) ((JSONObject) j).get("formatted") + "; ";
				}
				description = description + "\n";
			} catch (JSONException e) {
				description = description + "���. �������: �� ���������" + "\n";
			}
			
			//���������� ����� ������ � ��������
			try {
				description = description + "����� ������: " + (String) ((JSONObject) i).getJSONObject("properties")
																						.getJSONObject("CompanyMetaData")
					        															.getJSONObject("Hours") 
					        															.get("text") + "\n";
			} catch (JSONException e) {
				description = description + "����� ������: �� ����������" + "\n";
			}
			
			//���������� ��������� � ��������
			try {
				JSONArray categoryArray = ((JSONObject) i).getJSONObject("properties")
													    .getJSONObject("CompanyMetaData")
													    .getJSONArray("Categories");
				description = description + "���������: ";
				for (Object j : categoryArray) {
					description = description + (String) ((JSONObject) j).get("name") + "; ";
				}
				description = description + "\n";
			} catch (JSONException e) {
				description = description + "���������: �� ����������" + "\n";
			}

			description = description + "����������: " + coordinates;
			
			MapObject temp = new MapObject(coordinates, name, description);
			mapObjects.add(temp);
			
		}
		
		return mapObjects;
		
	}
	
	public static String getRequest(String coordinates, int scale, boolean isSatellite, boolean isTraffic) {
		
		String typeMap = "";
		String traffic = "";
		
		if (isSatellite) {
			typeMap = "sat";
		} else {
			typeMap = "map";
		}
		
		if (isTraffic) {
			traffic = ",trf";
		}
		
		return "https://static-maps.yandex.ru/1.x/?"
				+ "l=" + typeMap + traffic
				+ "&ll=" + coordinates
				+ "&size=650,450"
				+ "&z=" + scale
				+ "&pt=" + coordinates + ",flag";
		
	}
	
	public static Image getImage(String coordinates, int scale, boolean isSatellite, boolean isTraffic) {
		
		try {
			HttpRequest getRequest = HttpRequest.newBuilder()
					.uri(new URI(getRequest(coordinates, scale, isSatellite, isTraffic)))
					.GET()
					.build();
			
			HttpClient client = HttpClient.newHttpClient();
			
			HttpResponse<InputStream> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofInputStream());
			return ImageIO.read(getResponse.body());
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
