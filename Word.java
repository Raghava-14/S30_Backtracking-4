/*A string S represents a list of words.

Each letter in the word has one or more options. If there is one option, the letter is represented as is. If there is more than one option, then curly braces delimit the options. For example, "{a,b,c}" represents options ['a', 'b', 'c'].

For example, "{a,b,c}d{e,f}" represents the list ['ade', 'adf', 'bde', 'bdf', 'cde', 'cdf'].

Return all words that can be formed in this manner, in lexicographical order.
  
  
Examples:
Input: "{a,b}c{d,e}f"
Output: ["acdf","acef","bcdf","bcef"]
Explanation: The possible combinations are "acdf", "acef", "bcdf", and "bcef", formed by selecting one option from the braces in each group.

Input: "{z}"
Output: ["z"]
Explanation: There is only one option for the letter 'z', so the only possible word is "z".

------------------------------------------------------------------------------------------------*/

//Time = O(N * 2^M), where N is the length of the input string and M is the number of groups of options
//Space = O(N), where N is the length of the input string

class Solution {
    public String[] expand(String s) {
        List<String> res = new ArrayList<>(); // Store the result
        backtrack(s.toCharArray(), 0, new StringBuilder(), res); // Start backtracking from index 0
        Collections.sort(res); // Sort the result in lexicographically increasing order
        return res.toArray(new String[res.size()]); // Convert the result to an array and return
    }
    
    private void backtrack(char[] s, int index, StringBuilder sb, List<String> res) {
        if (index == s.length) { // If we have processed all characters in the string
            res.add(sb.toString()); // Add the current combination to the result
            return;
        }
        
        if (s[index] == '{') { // If we encounter a brace
            int end = index;
            while (s[end] != '}') { // Find the matching closing brace
                end++;
            }
            char[] options = Arrays.copyOfRange(s, index+1, end); // Extract the options inside the braces
            Arrays.sort(options); // Sort the options in lexicographically increasing order
            for (char c : options) { // For each option
                sb.append(c); // Add the option to the current combination
                backtrack(s, end+1, sb, res); // Recursively process the rest of the string
                sb.deleteCharAt(sb.length()-1); // Remove the last added option
            }
        } else { // If we encounter a non-brace character
            sb.append(s[index]); // Add the character to the current combination
            backtrack(s, index+1, sb, res); // Recursively process the rest of the string
            sb.deleteCharAt(sb.length()-1); // Remove the last added character
        }
    }
}
