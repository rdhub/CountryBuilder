public class City
{
	private String name;
	private int population, farmTotal, houseTotal, factoryTotal, storeTotal;
	private int farm2Total, house2Total, factory2Total, store2Total;
	private int farmWorkerTotal, factoryWorkerTotal, storeWorkerTotal, workerTotal;
	private int cityValue, cityFunds, resources, goods, unemployed, cityMaintenance, unemploymentRate;
	private int requiredReserves, excessReserves, demandDeposits;
	private int[] storePlot;
	private int[][] farmPlot, housePlot, factoryPlot;
	private int[][] farmWorkers, factoryWorkers, storeWorkers;
	
	public City(String n, int farmNum, int houseNum, int factoryNum, int storeNum)
	{
		name = n;
		population = 100;
		cityFunds = 50000;
		farmTotal = houseTotal = factoryTotal = storeTotal = 1;
		farm2Total = house2Total = factory2Total = store2Total = 0;
		excessReserves = 0;
		requiredReserves = 10000;
		demandDeposits = 100000;
		cityValue = farmTotal * 15000 + houseTotal * 25000 + factoryTotal * 30000 + storeTotal * 30000;
		cityMaintenance = 0;
		resources = goods = 50000;
		unemployed = 0;
		farmWorkerTotal = factoryWorkerTotal = storeWorkerTotal = 0;
		farmPlot = new int[4][farmNum/4];
		farmWorkers = new int[4][farmNum/4];
		housePlot = new int[2][houseNum/2];
		factoryPlot = new int[2][factoryNum/2];
		factoryWorkers = new int[2][factoryNum/2];
		storePlot = new int[storeNum];
		storeWorkers = new int[storeNum][1];
		farmPlot[0][0] = 1;
		housePlot[0][0] = 1;
		factoryPlot[0][0] = 1;
		storePlot[0] = 1;
	}
	//Getter methods
	public int getRequiredReserves()
	{
		return requiredReserves;
	}
	public int getExcessReserves()
	{
		return excessReserves;
	}
	public int getDemandDeposits()
	{
		return demandDeposits;
	}
	public int getUnemploymentRate()
	{
		return (int)((double)unemployed/population * 100);
	}
	public int getCityMaintenance()
	{
		return cityMaintenance;
	}
	public int getResources()
	{
		return resources;
	}
	public int getGoods()
	{
		return goods;
	}
	public int getCityFunds()
	{
		return cityFunds;
	}
	public int getCityValue()
	{
		return cityValue;
	}
	public int getPopulation()
	{
		return population;
	}
	public int getUnemployed()
	{
		calculateUnemployed();
		return unemployed;
	}
	
	public int getFarmTotal()
	{
		return farmTotal;
	}
	
	public int getHouseTotal()
	{
		return houseTotal;
	}
	
	public int getFactoryTotal()
	{
		return factoryTotal;
	}
	
	public int getStoreTotal()
	{
		return storeTotal;
	}
	
	public int getFarm2Total()
	{
		return farm2Total;
	}
	
	public int getHouse2Total()
	{
		return house2Total;
	}
	
	public int getFactory2Total()
	{
		return factory2Total;
	}
	
	public int getStore2Total()
	{
		return store2Total;
	}
	
	public int getFarmWorkerTotal()
	{
		return farmWorkerTotal;
	}
	
	public int getFactoryWorkerTotal()
	{
		return factoryWorkerTotal;
	}
	
	public int getStoreWorkerTotal()
	{
		return storeWorkerTotal;
	}
	
	public int getWorkerTotal()
	{
		addWorkers();
		return workerTotal;
	}
	//Setter methods
	public void changeRequiredReserves(int amount)
	{
		requiredReserves += amount;
	}
	public void changeExcessReserves(int amount)
	{
		excessReserves += amount;
	}
	public void changeDemandDeposits(int amount)
	{
		demandDeposits += amount;
	}
	public void changeFarmWorkerCount(int count)
	{
		farmWorkerTotal += count;
	}
	public void changeFactoryWorkerCount(int count)
	{
		factoryWorkerTotal += count;
	}
	public void changeStoreWorkerCount(int count)
	{
		storeWorkerTotal += count;
	}
	public void changeResources(int amount)
	{
		resources += amount;
	}
	public void changeGoods(int amount)
	{
		goods += amount;
	}
	public void changeCityFunds(int funds)
	{
		cityFunds += funds;
	}
	public void changeCityValue(int value)
	{
		cityValue += value;
	}
	public void changePopulation(int pop)
	{
		population += pop;
	}
	
	public void changeFarmCount(int count)
	{
		farmTotal += count;
	}
	
	public void changeHouseCount(int count)
	{
		houseTotal += count;
	}
	
	public void changeFactoryCount(int count)
	{
		factoryTotal += count;
	}
	
	public void changeStoreCount(int count)
	{
		storeTotal += count;
	}
	public void changeFarm2Count(int count)
	{
		farm2Total += count;
	}
	
	public void changeHouse2Count(int count)
	{
		house2Total += count;
	}
	
	public void changeFactory2Count(int count)
	{
		factory2Total += count;
	}
	
	public void changeStore2Count(int count)
	{
		store2Total += count;
	}
	
	public void changeFarmPlotElement(int building, int x, int y)
	{
		farmPlot[y][x] += building;
	}
	
	public void changeFarmWorkersElement(int num, int x, int y)
	{
		farmWorkers[y][x] += num;
	}
	
	public void changeHousePlotElement(int building, int x, int y)
	{
		housePlot[y][x] += building;
	}
	
	public void changeFactoryPlotElement(int building, int x, int y)
	{
		factoryPlot[y][x] += building;
	}
	
	public void changeFactoryWorkersElement(int num, int x, int y)
	{
		factoryWorkers[y][x] += num;
	}
	
	public void changeStorePlotElement(int building, int x)
	{
		storePlot[x] += building;
	}
	
	public void changeStoreWorkersElement(int num, int x)
	{
		storeWorkers[x][0] += num;
	}
	//Plot getter methods
	public int[][] getFarmPlot()
	{
		return farmPlot;
	}
	
	public int[][] getFarmWorkers()
	{
		return farmWorkers;
	}
	
	public int[][] getHousePlot()
	{
		return housePlot;
	}
	
	public int[][] getFactoryPlot()
	{
		return factoryPlot;
	}
	
	public int[][] getFactoryWorkers()
	{
		return factoryWorkers;
	}
	
	public int[] getStorePlot()
	{
		return storePlot;
	}
	
	public int[][] getStoreWorkers()
	{
		return storeWorkers;
	}
	public String toString()
	{
		return name;
	}
	public void addWorkers()
	{
		workerTotal = farmWorkerTotal + factoryWorkerTotal + storeWorkerTotal;
	}
	public void calculateUnemployed()
	{
		unemployed = population - workerTotal;
	}
	public void calcCityMaintenanceValue(int farmCost, int farm2Cost, int factoryCost, int factory2Cost, int houseCost, int house2Cost, int storeCost, int store2Cost)
	{
		cityMaintenance = farmTotal * farmCost/10 + houseTotal * houseCost/10 + factoryTotal * factoryCost/10 + storeTotal * storeCost/10;
		cityMaintenance += farm2Total * farm2Cost/10 + house2Total * house2Cost/10 + factory2Total * factory2Cost/10 + store2Total * store2Cost/10;
		cityValue = farmTotal * farmCost + houseTotal * houseCost + factoryTotal * factoryCost + storeTotal * storeCost;
		cityValue += farm2Total * farm2Cost + house2Total * house2Cost + factory2Total * factory2Cost + store2Total * store2Cost;
	}
}
