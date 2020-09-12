public class EndState
{
	private String state;
	private String orientation;
	private int refRow;

	public EndState(String state)
	{
		this.state = state;
	}

	public EndState(String state, String orientation)
	{
		this(state);
		this.orientation = orientation;
	}

	public EndState(String state, String orientation, int refRow)
	{
		this(state, orientation);
		this.refRow = refRow;
	}

	public String getState()
	{
		return state;
	}

	public String getOrientation()
	{
		return orientation;
	}

	public int getRefRow()
	{
		return refRow;
	}
}
