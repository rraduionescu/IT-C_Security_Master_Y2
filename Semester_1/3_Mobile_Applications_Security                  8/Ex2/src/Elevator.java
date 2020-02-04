class Elevator
{
	private int selectedFloor;
	private boolean areDoorsOpen;

	public int getSelectedFloor()
	{
		return selectedFloor;
	}

	public void setSelectedFloor(int selectedFloor)
	{
		if(selectedFloor >=0 && selectedFloor <= 5)
		{
			this.selectedFloor = selectedFloor;
		}
	}

	public boolean areDoorsOpen()
	{
		return areDoorsOpen;
	}

	public void setAreDoorsOpen(boolean areDoorsOpen)
	{
		this.areDoorsOpen = areDoorsOpen;
	}
}
