package pomoc.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserTest {
	public static void main(String[] args) {
		String subject ="Re: #1111";
		Pattern pattern = Pattern.compile("#\\d+");
		Matcher matcher = pattern.matcher(subject);
     	if(matcher.find()) {
     		String number = matcher.group();
     		System.out.println(number.substring(1));
     	}
	}
}
