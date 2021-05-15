package net.oldschoolminecraft.nlib.exceptions;

public class AutoCompleteChoicesException extends RuntimeException
{
    protected String[] choices;
    protected String argName;

    public AutoCompleteChoicesException(final String[] choices, final String argName)
    {
        this.choices = choices;
        this.argName = argName;
    }

    public String getArgName()
    {
        return this.argName;
    }

    public String[] getChoices()
    {
        return this.choices;
    }
}

