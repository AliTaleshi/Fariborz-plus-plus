package ir.ac.kntu;

import java.util.*;
import java.util.regex.Pattern;

public class Main {
	
	static final Map<String, Integer> map = new HashMap<>();
	
	public static void output(String input) {
		
		Iterator<Map.Entry<String, Integer> > iterator = map.entrySet().iterator();
		boolean isKeyPresent = false;
		
		while(iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			
			if(input.substring(1).equals(entry.getKey())) {
				isKeyPresent = true;
			}
		}
		
		if(isKeyPresent == true) {
			System.out.println(map.get(input.substring(1)));
		} else {
			System.out.println("variable is not defined");
		}
	}
	
	public static void innput(String input) {
		
		Scanner scan = new Scanner(System.in);
		String t = "^[a-zA-Z]*$";
		boolean b = Pattern.compile(t).matcher(input.substring(1)).matches();
		
		if(b) {
			String y = scan.nextLine();
			int x = Integer.parseInt(y);
			map.put(input.substring(1), x);
		} else {
			System.out.println("variable is not defined");
		}
	}
	
	public static void SecondPart(String input) {
		String[] aa = input.split("=");
		if(Pattern.compile("^[1-9]*$").matcher(aa[1]).matches()) { // this is a part of the first part of the project and if it was a number
			map.put(aa[0], Integer.parseInt(aa[1]));
		} else if(Pattern.compile("^[a-zA-Z]*$").matcher(aa[1]).matches()) { // if it was a variable
			map.put(aa[0], map.get(aa[1]));
		} else if(Pattern.compile("^(?=.*[A-Za-z1-9])(?=.*\\d)(?=.*[-+]).+$").matcher(aa[1]).matches()) { // if it was a combination of numbers and characters
			
			String[] bb = aa[1].split("[-+]");
			int i = 0, finalans = 0, len = 0;
			
			while(i != bb.length-1) { // this is the second part of the project

				if(Pattern.compile("^[a-zA-Z]*$").matcher(bb[i]).matches()) { // if it was a variable
					if(map.get(bb[i]) != null) { // if variable was defined calculate the final answer
						if(aa[1].substring(len + bb[i].length(), len + bb[i].length()+1).equals("+")) {
							if(i == 0) {
								finalans = map.get(bb[0]);
							} else if(i != 0 && i != aa[1].length()-1) {
								finalans += map.get(bb[i]);
								len += bb[i].length()+1;
							}
							
						} else if(aa[1].substring(len + bb[i].length(), len + bb[i].length()+1).equals("-")) {
							if(i == 0) {
								finalans = map.get(bb[0]);
							} else if(i != 0 && i != aa[1].length()-1) {
								finalans -= map.get(bb[i]);
								len += bb[i].length()+1;
							}
							
						}
					} else { // if not defined print the error and continue the program
						System.out.println("variable is not defined");
					}
				} else if(Pattern.compile("^[1-9]*$").matcher(bb[i]).matches()) { // if it was a number
					// calculation part
					if(aa[1].substring(len + bb[i].length(), len + bb[i].length()+1).equals("+")) {
						if(i == 0) {
							finalans = Integer.parseInt(bb[0]);
						} else if(i != 0 && i != aa[1].length()-1) {
							finalans += Integer.parseInt(bb[i]);
							len += bb[i].length()+1;
						}
						
					} else if(aa[1].substring(len + bb[i].length(), len + bb[i].length()+1).equals("-")) {
						if(i == 0) {
							finalans = Integer.parseInt(bb[0]);
						} else if(i != 0 && i != aa[1].length()-1) {
							finalans -= Integer.parseInt(bb[i]);
							len += bb[i].length()+1;
						}
					}
				}
				i++;
			}
			if(Pattern.compile("^[a-zA-Z]*$").matcher(bb[bb.length-1]).matches()) {
				if(map.get(bb[bb.length-1]) != null) {
					if(aa[1].substring(aa[1].length()-bb[bb.length-1].length()-1, aa[1].length()-bb[bb.length-1].length()).equals("-")) {
						finalans -= map.get(bb[bb.length-1]);
					}
					if(aa[1].substring(aa[1].length()-bb[bb.length-1].length()-1, aa[1].length()-bb[bb.length-1].length()).equals("+")) {
						finalans += map.get(bb[bb.length-1]);
					}
					
				} else {
					System.out.println("variable is not defined");
				}
				
			} else if(Pattern.compile("^[1-9]*$").matcher(bb[i]).matches()) {
				if(aa[1].substring(aa[1].length()-bb[bb.length-1].length()-1, aa[1].length()-bb[bb.length-1].length()).equals("-")) {
					finalans -= Integer.parseInt(bb[bb.length-1]);
				}
				
				if(aa[1].substring(aa[1].length()-bb[bb.length-1].length()-1, aa[1].length()-bb[bb.length-1].length()).equals("+")) {
					finalans += Integer.parseInt(bb[bb.length-1]);
				}
			}
			map.put(aa[0], finalans);
		}
	}
	
	public static void main(String[] args) {
        	Scanner scan = new Scanner(System.in);
		while(2 > 0) {
			String input = "";
			input = scan.nextLine();
			input = input.trim().replaceAll("\\s+", "");
			if(input.equals("exit_0")) {
				return;
			}
			if(input.substring(0, 1).equals(">")) {
				innput(input);
			}
			if(input.substring(0, 1).equals("<")) {
				output(input);
			}
			if(input.indexOf("=") != -1) {
				SecondPart(input);
			}
		}
	}
}