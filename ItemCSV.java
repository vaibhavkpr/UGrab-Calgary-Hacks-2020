import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class ItemCSV {
    private String filename = "";
    private ArrayList<FoodItem> itemNames = new ArrayList<>();

    public ItemCSV(String filename) {
        this.filename = filename;
        parseCSV();
    }

    private void parseCSV() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            
            // Read line to skip header.
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                
                String restrictions = row.length < 5 ? "All" : row[4];

                FoodItem item = new FoodItem(row[0], Double.parseDouble(row[2]), row[1], row[3], restrictions, row[6]);
                itemNames.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ie) {
                System.out.println("Error occurred while closing the BufferedReader");
                ie.printStackTrace();
            }
        }
    }

    public FoodItem getFoodItem(int index) {
        return new FoodItem(itemNames.get(index));
    }

    public ArrayList<FoodItem> getFoodList() {
        ArrayList<FoodItem> copy = new ArrayList<>();
        for (int i = 0; i < itemNames.size(); i++) {
            copy.add(new FoodItem(itemNames.get(i)));
        }
        return copy;
    }

    public static ArrayList<FoodItem> priceFilter(ArrayList<FoodItem> source, double min, double max)
    {
        ArrayList<FoodItem> filteredList = new ArrayList<>();
   
        for (int i = 0; i < source.size(); i++)
        {
            FoodItem checkItem = new FoodItem(source.get(i));
            if(checkItem.getPrice() <= max && checkItem.getPrice() >= min )
            {  
                filteredList.add(checkItem);
            }
        }
        return filteredList;
    }

    public static ArrayList<FoodItem> foodRestrictionFilter(ArrayList<FoodItem> source, String restriction)
    {
        ArrayList<FoodItem> filteredList = new ArrayList<>();
    
        for (int i = 0; i < source.size(); i++)
        {
            FoodItem checkItem = new FoodItem(source.get(i));
            String[] restrictionList = checkItem.getRestriction().split(";");
            for(int k = 0; k < restrictionList.length; k++)
            {
                if(restrictionList[k].equals(restriction))
                {
                filteredList.add(checkItem);
                }
            }
        }
        return filteredList;
    }
   
    public static ArrayList<FoodItem> foodTypeFilter(ArrayList<FoodItem> source, String foodType)
    {
        ArrayList<FoodItem> filteredList = new ArrayList<>();
    
        for (int i = 0; i < source.size(); i++)
        {
            FoodItem checkItem = new FoodItem(source.get(i));
            if(checkItem.getType().equals(foodType))
            {
                filteredList.add(checkItem);
            }
        }
        return filteredList;
    }
   
    public static ArrayList<FoodItem> storeFilter(ArrayList<FoodItem> source, String store)
    {
        ArrayList<FoodItem> filteredList = new ArrayList<>();
   
        for (int i = 0; i < source.size(); i++)
        {
            FoodItem checkItem = new FoodItem(source.get(i));
            if(checkItem.getStoreName().equals(store))
            {  
                filteredList.add(checkItem);
            }
        }
        return filteredList;
    }
   
    public static ArrayList<FoodItem> searchFilter(ArrayList<FoodItem> source, String search)
    {
        ArrayList<FoodItem> filteredList = new ArrayList<>();
   
        for (int i = 0; i < source.size(); i++)
        {
            FoodItem checkItem = new FoodItem(source.get(i));
            if(checkItem.getName().toLowerCase().contains(search.toLowerCase()))
            {  
                filteredList.add(checkItem);
            }
        }
        return filteredList;
    }
    
    public static void highestToLowest(ArrayList<FoodItem> source)
    {
    	for (int i = 0; i < source.size(); i++)
    	{
    		int largestIndex = i;
    		
    		for(int j = i; j < source.size(); j++)
    		{
    			if(source.get(largestIndex).getPrice() < source.get(j).getPrice())
    			{
    				largestIndex = j;
    			}
    		}
    		Collections.swap(source,largestIndex,i);
    	}
    }
    
    public static void lowestToHighest(ArrayList<FoodItem> source)
    {
    	for (int i = 0; i < source.size(); i++)
    	{
    		int largestIndex = i;
    		
    		for(int j = i; j < source.size(); j++)
    		{
    			if(source.get(largestIndex).getPrice() > source.get(j).getPrice())
    			{
    				largestIndex = j;
    			}
    		}
    		Collections.swap(source,largestIndex,i);
    	}
    }

    public static void main(String[] args) 
    {
        ItemCSV test = new ItemCSV("items.csv");
        ArrayList<FoodItem> original = test.getFoodList();
        ArrayList<FoodItem> updatedList = new ArrayList<>(original);
        //updatedList = priceFilter(updatedList,2,4);
        updatedList = foodRestrictionFilter(updatedList,"Vegan");
        // updatedList = foodTypeFilter(updatedList,"Sweets");
    //    updatedList = storeFilter(updatedList, "A & W");
        //updatedList = searchFilter(updatedList, "baNaNa");
        lowestToHighest(updatedList);

        for(int i = 0; i < updatedList.size(); i++)
        {
            System.out.println(updatedList.get(i).toString());
            System.out.println(updatedList.get(i).getImage());
        }
    }
 
}

