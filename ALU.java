/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author 151250134_ 田原
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		int[] resultOfTemp = new int[length];
		if(number.startsWith("-")){
			number = number.substring(1);
			int temp = Integer.parseInt(number);
			int pointer = length;
			while((temp / 2) != 0){
				pointer--;
				resultOfTemp[pointer] = temp % 2;
				temp = temp / 2;
			}
			pointer--;
			resultOfTemp[pointer] = 1;
			//取反
			for(int i = 0; i < length; i++){
				resultOfTemp[i] = 1 - resultOfTemp[i];
			}
			int c = 1;                              //取反加1时的进位
			int pointer2 = length - 1;
			while((resultOfTemp[pointer2] + c) > 1){
				resultOfTemp[pointer2] = 0;
				pointer2--;
			}
			resultOfTemp[pointer2] = resultOfTemp[pointer2] + c;
		}
		else{
			Integer temp = Integer.parseInt(number);
			int pointer = length;
			while((temp / 2) != 0){
				pointer--;
				resultOfTemp[pointer] = temp % 2;
				temp = temp / 2;
			}
			pointer--;
			resultOfTemp[pointer] = temp % 2;
		}
		String result = "";
		for(int i : resultOfTemp){
			result = result + i;
		}
		return result;
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		int length = 1 + eLength + sLength;
		int eMax = (int)Math.pow(2, eLength - 1) - 1;                   //规格化最大指数
		double max = (2 - Math.pow(2, -sLength)) * Math.pow(2, eMax);   //规格化能表示的最大的数
		double min = Math.pow(2, -eMax + 1);                            //规格化能表示的最小的数
		
		int[] resultOfTemp = new int[length];
		if(number.startsWith("-")){
			resultOfTemp[0] = 1;
			number = number.substring(1);
		}
		//正负无穷
		if(Double.parseDouble(number) > max){
			for(int i = 1; i <= eLength + 1; i++){
				resultOfTemp[i] = 1;
			}
		}
		//规格化数
		else if(Double.parseDouble(number) >= min){
			int dot = number.indexOf(".");
			double part1 = Double.parseDouble(number.substring(0, dot));    //整数部分
			double part2 = Double.parseDouble(number) - part1;              //小数部分
			int[] temp1 = new int[eMax - 1 + sLength];
			int flag = 0;
			while((part2 * 2) - 1 != -1 && flag <temp1.length){
				if((part2 * 2) < 1){
					part2 = part2 * 2;
					temp1[flag] = 0;
				}
				else{
					part2 = part2 * 2 - 1;
					temp1[flag] = 1;
				}
				flag++;
			}
			int length1 = eMax + 2;
			String s1 = this.integerRepresentation(number.substring(0, dot), length1);
			for(int i : temp1){
				s1 = s1 + i;
			}
			char[] c2 = s1.toCharArray();
			int m = s1.indexOf("1");
			int count = length1 - m - 1;                       //小数点左移的次数
			int e = count + eMax;                              //指数部分的大小
			String s2 = this.integerRepresentation("" + e, eLength);
			char[] c1 = s2.toCharArray();
			for(int i = 1; i <= eLength; i++){
				resultOfTemp[i] = Integer.parseInt(String.valueOf(c1[i - 1]));
			}
			for(int i = eLength + 1; i < length; i++){
				resultOfTemp[i] = Integer.parseInt(String.valueOf(c2[m + 1]));
				m++;
			}		
		}
		//非规格化数
		else{
			int dot = number.indexOf(".");
			double part1 = Double.parseDouble(number.substring(0, dot));    //整数部分
			double part2 = Double.parseDouble(number) - part1;              //小数部分
			int[] temp1 = new int[eMax - 1 + sLength];
			int flag = 0;
			while((part2 * 2) - 1 != -1 && flag <temp1.length){
				if((part2 * 2) < 1){
					part2 = part2 * 2;
					temp1[flag] = 0;
				}
				else{
					part2 = part2 * 2 - 1;
					temp1[flag] = 1;
				}
				flag++;
			}
			String s1 = "";
			for(int i : temp1){
				s1 = s1 + i;
			}
			char[] c2 = s1.toCharArray();
			int m = c2.length - sLength;
			for(int i = eLength + 1; i < length; i++){
				resultOfTemp[i] = Integer.parseInt(String.valueOf(c2[m]));
				m++;
			}		
			
		}
		
		
		String result = "";
		for(int i : resultOfTemp){
			result = result + i;
		}
		return result;
	}
	
	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		if(length == 32){
			return this.floatRepresentation(number, 8, 23);
		}
		else{
			return this.floatRepresentation(number, 11, 52);
		}
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		char[] resultOfTemp = operand.toCharArray();
		long result = 0;
		result = result - (resultOfTemp[0] - '0') * (int)(Math.pow(2, resultOfTemp.length - 1));
		for(int i = 1; i < resultOfTemp.length; i++){
			result += (resultOfTemp[i] - '0') * (int)(Math.pow(2, resultOfTemp.length - i - 1));
		}
		return "" + result;
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		String realResult = "";
		char[] eTemp = operand.substring(1, eLength + 1).toCharArray();
		char[] temp = operand.substring(eLength + 1).toCharArray();
		double resultTemp = 0;
		for(int i = 0; i < temp.length; i++){
			resultTemp = resultTemp + (temp[i] - '0') * Math.pow(2, -(i + 1));
		}
		int eValueTemp = 0;
		for(int i = 0; i < eTemp.length; i++){
			eValueTemp = eValueTemp + (eTemp[i] - '0') * (int)(Math.pow(2, eTemp.length - i - 1));
		}
		double result = 0;
		if(eValueTemp == 0){
			int eValue = -((int)Math.pow(2, eLength - 1) - 2);
			result = resultTemp * Math.pow(2, eValue);
			realResult = realResult + result;
		}
		else if(eValueTemp == (int)Math.pow(2, eLength) - 1){
			if(resultTemp == 0){
				realResult = "Inf";
			}
			else{
				realResult = "NaN";
			}
		}
		else{
			int eValue = eValueTemp - ((int)Math.pow(2, eLength - 1) - 1);
			result = (resultTemp + 1.0) * Math.pow(2, eValue);
			realResult = realResult + result;
		}
		if(operand.startsWith("1") && !realResult.equals("NaN")){
			realResult = "-" + realResult;
		}
		return realResult;
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		String result = "";
		char[] temp = operand.toCharArray();
		for(char i : temp){
			if(i == '0'){
				result = result + "1";
			}
			else{
				result = result + "0";
			}
		}
		return result;
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		for(int i = 1; i <= n; i++){
			operand = operand.substring(1) + "0";
		}
		return operand;
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		for(int i = 1; i <= n; i++){
			operand = "0" + operand.substring(0, operand.length() - 1);
		}
		return operand;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		for(int i = 1; i <= n; i++){
			operand = operand.substring(0,1) + operand.substring(0, operand.length() - 1);
		}
		return operand;
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		String result = String.valueOf(this.xOrGate(this.xOrGate(x, y), c));
		char m = this.andGate(x, y);
		char n = this.andGate(x, c);
		char i = this.andGate(y, c);
		result = String.valueOf(this.orGate(this.orGate(m, n), i)) + result;
		return result;
	}
	private char andGate(char x, char y){
		if(x == '1' && y == '1'){
			return '1';
		}
		else{
			return '0';
		}
	}
	private char orGate(char x, char y){
		if(x == '0' && y == '0'){
			return '0';
		}
		else{
			return '1';
		}
	}
	private char xOrGate(char x, char y){
		if(x == y){
			return '0';
		}
		else{
			return '1';
		}
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		char[] P = new char[4];
		char[] G = new char[4];
		char[] C = new char[4];
		char[] S = new char[4];
		char[] X = operand1.toCharArray();
		char[] Y = operand2.toCharArray();
		for(int i = 0; i < 4; i++){
			P[i] = this.orGate(X[3 - i], Y[3 - i]);
			G[i] = this.andGate(X[3 - i], Y[3 - i]);
		}
		C[0] = this.orGate(G[0], this.andGate(P[0], c));
		C[1] = this.orGate(this.orGate(G[1], this.andGate(P[1], G[0])), this.andGate(this.andGate(P[1], P[0]), c));
		C[2] = this.orGate(this.orGate(this.orGate(G[2], this.andGate(P[2], G[1])), this.andGate(this.andGate(P[2], P[1]), G[0])), this.andGate(this.andGate(P[2], P[1]), this.andGate(P[0], c)));
		C[3] = this.orGate(this.orGate(this.orGate(G[3], this.andGate(P[3], G[2])), this.orGate(this.andGate(P[3], this.andGate(P[2], G[1])), this.andGate(this.andGate(P[3], P[2]), this.andGate(P[1], G[0])))), this.andGate(this.andGate(this.andGate(P[3], P[2]), this.andGate(P[1], P[0])), c));
		for(int i = 0; i < 4; i++){
			S[i] = this.fullAdder(X[3 - i], Y[3 - i], c).charAt(1);
			c = this.fullAdder(X[3 - i], Y[3 - i], c).charAt(0);
		}
		String result = "";
		for(int i = 0; i < 4; i++){
			result = result + S[3 - i];
		}
		result = String.valueOf(C[3]) + result;
		return result;
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		String result = "";
		char c = '1';
		for(int i = operand.length() - 1; i >= 0; i--){
			result = String.valueOf(this.xOrGate(operand.charAt(i), c)) + result;
			c = this.andGate(operand.charAt(i), c);
		}
		result = String.valueOf(c) + result;
		return result;
	}
	
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", '0', 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		String result = "";
		int n = length / 4;
		int length1 = operand1.length();
		int length2 = operand2.length();
		if(operand1.length() < length){
			for(int i = 1; i <= length - length1; i++){
				operand1 = operand1.substring(0, 1) + operand1;
			}
		}
		if(operand2.length() < length){
			for(int i = 1; i <= length - length2; i++){
				operand2 = operand2.substring(0, 1) + operand2;
			}
		}
		for(int i = 1; i <= n; i++){
			result = this.claAdder(operand1.substring(length - i * 4, length - (i - 1) * 4), operand2.substring(length - i * 4, length - (i - 1) * 4), c).substring(1) + result;
			c = this.claAdder(operand1.substring(length - i * 4, length - (i - 1) * 4), operand2.substring(length - i * 4, length - (i - 1) * 4), c).charAt(0);
		}
		if(operand1.charAt(0) == operand2.charAt(0) && result.charAt(0) != operand1.charAt(0)){
			result = "1" + result;
		}
		else{
			result = "0" + result;
		}
		return result;
	}
	
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return this.adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		operand2 = this.negation(operand2);
		return this.adder(operand1, operand2, '1', length);
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		int length1 = operand1.length();
		int length2 = operand2.length();
		if(operand1.length() < length){
			for(int i = 1; i <= length - length1; i++){
				operand1 = operand1.substring(0, 1) + operand1;
			}
		}
		if(operand2.length() < length){
			for(int i = 1; i <= length - length2; i++){
				operand2 = operand2.substring(0, 1) + operand2;
			}
		}
		char[] temp = operand2.toCharArray();
		char[] temp1 = new char[2 * length + 1];
		for(int i = 0; i < length; i++){
			temp1[i] = '0';
		}
		for(int i = length; i < 2 * length; i++){
			temp1[i] = temp[i - length];
		}
		temp1[2 * length] = '0';
		String temp2 = new String(temp1);
		for(int i = 1; i <= length; i++){
			
			if(temp1[2 * length] - temp1[2 * length - 1] == 0){
				temp2 = this.ariRightShift(temp2, 1);
				temp1 = temp2.toCharArray();
			}
			else if(temp1[2 * length] - temp1[2 * length - 1] == 1){
				String s = new String(temp1, 0, length);
				String s1 = this.integerAddition(operand1, s, length);
				String s2 = new String(temp1, length, length +1);
				temp2 = s1.substring(1) + s2;
				temp2 = this.ariRightShift(temp2, 1);
				temp1 = temp2.toCharArray();
			}
			else if(temp1[2 * length] - temp1[2 * length - 1] == -1){
				String s = new String(temp1, 0, length);
				String s1 = this.integerSubtraction(s, operand1, length);
				String s2 = new String(temp1, length, length +1);
				temp2 = s1.substring(1) + s2;
				temp2 = this.ariRightShift(temp2, 1);
				temp1 = temp2.toCharArray();
			}
			
		}
		String test = "";
		if(operand1.charAt(0) == operand2.charAt(0)){
			for(int i = 1; i <= length + 1; i++){
				test = test + "0";
			}
		}
		else{
			for(int i = 1; i <= length + 1; i++){
				test = test + "1";
			}
		}
		String result = "";
		if(temp2.substring(0, length + 1).equals(test)){
			result = "0" + temp2.substring(length, 2 * length);
		}
		else{
			result = "1" + temp2.substring(length, 2 * length);
		}
		
		return result;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		int length1 = operand1.length();
		int length2 = operand2.length();
		if(operand1.length() < length){
			for(int i = 1; i <= length - length1; i++){
				operand1 = operand1.substring(0, 1) + operand1;
			}
		}
		if(operand2.length() < length){
			for(int i = 1; i <= length - length2; i++){
				operand2 = operand2.substring(0, 1) + operand2;
			}
		}
		String remainder = "";
		char test = operand1.charAt(0);
		for(int i = 1; i <= length; i++){
			remainder = remainder + operand1.substring(0, 1);
		}
		for(int j = 1; j <= length; j++){
			if(remainder.charAt(0) == operand2.charAt(0)){
				remainder = this.integerSubtraction(remainder, operand2, length).substring(1);
			}
			else{
				remainder = this.integerAddition(remainder, operand2, length).substring(1);
			}
			if(remainder.charAt(0) == operand2.charAt(0)){
				String temp = this.leftShift(remainder + operand1, 1);
				remainder = temp.substring(0, length);
				operand1 = temp.substring(length, 2 * length - 1) + "1";
			}
			else{
				String temp = this.leftShift(remainder + operand1, 1);
				remainder = temp.substring(0, length);
				operand1 = temp.substring(length, 2 * length - 1) + "0";
			}
		}
		if(remainder.charAt(0) == operand2.charAt(0)){
			remainder = this.integerSubtraction(remainder, operand2, length).substring(1);
		}
		else{
			remainder = this.integerAddition(remainder, operand2, length).substring(1);
		}
		if(remainder.charAt(0) == operand2.charAt(0)){
			String temp = this.leftShift(remainder + operand1, 1);
			operand1 = temp.substring(length, 2 * length - 1) + "1";
		}
		else{
			String temp = this.leftShift(remainder + operand1, 1);
			operand1 = temp.substring(length, 2 * length - 1) + "0";
		}
		if(operand1.charAt(0) == '1'){
			operand1 = this.oneAdder(operand1).substring(1);
		}
		if(remainder.charAt(0) != test){
			if(operand2.charAt(0) == test){
				remainder = this.integerAddition(remainder, operand2, length).substring(1);
			}
			else{
				remainder = this.integerSubtraction(remainder, operand2, length).substring(1);
			}
		}
		String result = "0" + operand1 + remainder;
		return result;
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		String result = "";
		int length1 = operand1.length();
		int length2 = operand2.length();
		String operand1temp = "0" + operand1.substring(1);
		String operand2temp = "0" + operand2.substring(1);
		if(operand1.length() < length){
			for(int i = 1; i <= length - length1; i++){
				operand1temp = "0" + operand1temp;
			}
		}
		if(operand2.length() < length){
			for(int i = 1; i <= length - length2; i++){
				operand2temp = "0" + operand2temp;
			}
		}
		if(operand1.charAt(0) == operand2.charAt(0)){
			result = this.integerAddition(operand1temp, operand2temp, length);
			int max = length1;
			if(length1 < length2){
				max = length2;
			}
			if(result.charAt(length - max + 1) == '1'){
				if(max == length){
					result = "1" + operand1.substring(0, 1) + result.substring(1);
				}
				else{
					result = "1" + operand1.substring(0, 1) + result.substring(1);
				}
			}
			else{
				result = "0" + operand1.substring(0, 1) + result.substring(1);
			}
		}
		else{
			result = this.integerSubtraction(operand1.substring(1), operand2.substring(1), length);
			int max = length1;
			if(length1 < length2){
				max = length2;
			}
			if(result.charAt(length - max + 1) == '1'){
				if(operand1.substring(0, 1).equals("0")){
					result = "0" + "1" + this.oneAdder(this.negation(result.substring(1))).substring(1);
				}
				else{
					result = "0" + "0" + this.oneAdder(this.negation(result.substring(1))).substring(1);
				}
			}
			else{
				if(max == length){
					result = "0" + operand1.substring(0, 1) + "0" + result.substring(length + 2 - max);
				}
				else{
					result = "0" + operand1.substring(0, 1) + result.substring(1, length - max) + "0" + result.substring(length + 2 - max);
				}
				
				
			}
		}
		return result;
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		String result = "";
		if(this.isZero(operand1.substring(1), sLength + eLength)){
			result = "0" + operand2;
			return result;
		}
		if(this.isZero(operand2.substring(1), sLength + eLength)){
			result = "0" + operand1;
			return result;
		}
		for(int i = 1; i <= gLength - 2; i++){
			operand1 = operand1 + "0";
			operand2 = operand2 + "0";
		}
		if(this.largerOne(operand1.substring(1, eLength + 1), operand2.substring(1, eLength + 1)) == 1){
			operand2 = operand2.substring(0, 1) + this.oneAdder(operand2.substring(1, eLength + 1)).substring(1) + operand2.substring(eLength + 1);
			operand2 = operand2.substring(0, eLength + 1) + "1" + this.logRightShift(operand2.substring(eLength + 1), 1).substring(1);
			while(!operand1.substring(1, eLength + 1).equals(operand2.substring(1, eLength + 1))){
				operand2 = operand2.substring(0, 1) + this.oneAdder(operand2.substring(1, eLength + 1)).substring(1) + operand2.substring(eLength + 1);
				operand2 = operand2.substring(0, eLength + 1) + this.logRightShift(operand2.substring(eLength + 1), 1);
				if(this.isZero(operand2.substring(eLength + 1), sLength + gLength)){
					result = "0" + operand1.substring(0, 1 + sLength + eLength);
					return result;
				}
			}
		}
		else if(this.largerOne(operand1.substring(1, eLength + 1), operand2.substring(1, eLength + 1)) == 2){
			operand1 = operand1.substring(0, 1) + this.oneAdder(operand1.substring(1, eLength + 1)).substring(1) + operand1.substring(eLength + 1);
			operand1 = operand1.substring(0, eLength + 1) + "1" + this.logRightShift(operand1.substring(eLength + 1), 1).substring(1);
			while(!operand1.substring(1, eLength + 1).equals(operand2.substring(1, eLength + 1))){
				operand1 = operand1.substring(0, 1) + this.oneAdder(operand1.substring(1, eLength + 1)).substring(1) + operand1.substring(eLength + 1);
				operand1 = operand1.substring(0, eLength + 1) + this.logRightShift(operand1.substring(eLength + 1), 1);
				if(this.isZero(operand1.substring(eLength + 1), sLength + gLength)){
					result = "0" + operand1.substring(0, 1 + sLength + eLength);
					return result;
				}
			}
		}
		String temp3 = this.signedAddition(operand1.substring(0, 1) + "1" + operand1.substring(eLength + 1), operand2.substring(0, 1) + "1" + operand2.substring(eLength + 1), sLength + gLength);
		result = "0" + temp3.substring(1, 2) + operand1.substring(1, 1 + eLength) + temp3.substring(3, 3 + sLength);
		if(this.isZero(temp3.substring(2, 2 + sLength), sLength)){
			String temp1 = "";
			for(int i = 1; i <= eLength; i++){
				temp1 = temp1 + "0";
			}
			result = result.substring(0, 2) + temp1 + result.substring(2 + eLength);
			return result;
		}
		if(temp3.substring(0, 1).equals("1")){
			result = result.substring(0, 2 + eLength) + temp3.substring(3, 3 + sLength);
			String temp2 = this.oneAdder(result.substring(2, eLength + 2));
			String temp4 = "";
			for(int i = 1; i <= eLength; i++){
				temp4 = temp4 + "1";
			}
			String stemp = "";
			for(int j = 1; j <= sLength; j++){
				stemp = stemp + "0";
			} 
			if(temp4.equals(temp2.substring(1))){
				result = "1" + result.substring(1, 2) + temp2.substring(1) + stemp;
			}
			else{
				result = "0" + result.substring(1, 2) + temp2.substring(1) + result.substring(2 + eLength);
			}
			
			return result;
		}
		if(temp3.substring(2, 3).equals("1")){
			return result;
		}
//		temp3 = temp3.substring(0, 2) + this.leftShift(temp3.substring(2), 1);
		String temp5 = "";
		for(int i = 1; i <= eLength; i++){
			temp5 = temp5 + "0";
		}
		while(!temp3.substring(2, 3).equals("1")){
			temp3 = temp3.substring(0, 2) + this.leftShift(temp3.substring(2), 1);
			result = result.substring(0, 2) + this.oneSub(result.substring(2, 2 + eLength), eLength) + temp3.substring(3, 3 + eLength);
			if(result.substring(2, 2 + eLength).equals(temp5)){
				result = "0" + result.substring(1, 2 + eLength) + temp3.substring(2, 2 + sLength);
				return result;
			}
		}
		result = "0" + result.substring(1, 2 + eLength) + temp3.substring(3, 3 + eLength);
		return result;
	}
	//判断操作数是否为0
		private boolean isZero(String operand, int length){
			boolean result = false;
			String temp = "";
			for(int i = 1; i <= length; i++){
				temp = temp + "0";
			}
			if(operand.equals(temp)){
				result = true;
			}
			return result;
		}
		//判断两个移码数的大小,返回值为0时表示相等，返回1时表示operand1稍大，返回2时表示operand2稍大
		private int largerOne(String operand1, String operand2){
			if(operand1.equals(operand2)){
				return 0;
			}
			else{
				int i = 0;
				while(operand1.charAt(i) == operand2.charAt(i)){
					i++;
				}
				if(operand1.charAt(i) == 1){
					return 1;
				}
				else{
					return 2;
				}
			}
		}
		//减1器
		private String oneSub(String operand, int length){
			char[] temp1 = operand.toCharArray();
			char[] temp2 = new char[length];
			char c = '0';
			for(int i = length - 1; i >= 0; i--){
				temp2[i] = this.fullAdder(temp1[i], '1', c).charAt(1);
				c = this.fullAdder(temp1[i], '1', c).charAt(0);
			}
			String result = new String(temp2);
			return result;
		}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		if(operand2.startsWith("0")){
			operand2 = "1" + operand2.substring(1);
		}
		else{
			operand2 = "0" + operand2.substring(1);
		}
		return this.floatAddition(operand1, operand2, eLength, sLength, gLength);
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		String test = "";
		String result = "";
		String test1  = "";
		for(int i = 1; i <= eLength; i++){
			test1 = test1 + "1";
		}
		if(operand1.charAt(0) == operand2.charAt(0)){
			result = "0";
		}
		else{
			result = "1";
		}
		for(int i = 1; i <= eLength + sLength; i++){
			test = test + "0";
		}
		if(operand1.substring(1).equals(test)){
			return "00" + test;
		}
		if(operand2.substring(1).equals(test)){
			return "00" + test;
		}
		String temp1 = "0000" + operand1.substring(1, 1 + eLength);
		String temp2 = "0000" + operand2.substring(1, 1 + eLength);
		String temp3 = "11111";
		for(int i = 1; i < eLength - 1; i++){
			temp3 = temp3 + "0";
		}
		temp3 = temp3 + "1";
		String tempOfE1 = this.integerAddition(temp1, temp2, eLength + 4).substring(1);
		String tempOfE =  this.integerAddition(tempOfE1, temp3, eLength + 4).substring(5);
		String tempTest = this.integerAddition(tempOfE1, temp3, eLength + 4).substring(1, 5);
		if(!tempTest.equals("0000")){
			result = "1" + result + test1 + test.substring(eLength);
			return result;
		}
		String tempOfS = this.integerMultiplication2("01" + operand1.substring(1 + eLength), "01" + operand2.substring(1 + eLength), 4 * sLength);
		char[] temp4 = tempOfS.toCharArray();
		int temp5 = 0;
		for(int i = 1; i < temp4.length; i++){
			if(temp4[i] == '1'){
				temp5 = i;
				break;
			}
		}
		if(tempTest.equals("0000") && tempOfE.equals(test.substring(0, eLength))){
			result = "0" + result + tempOfE + tempOfS.substring(temp5,  temp5 + sLength);
			return result;
		}
		result = "0" + result + tempOfE + tempOfS.substring(temp5 + 1,  temp5 + 1 + sLength);
		return result;
	}
	private String integerMultiplication2 (String operand1, String operand2, int length) {
		int length1 = operand1.length();
		int length2 = operand2.length();
		if(operand1.length() < length){
			for(int i = 1; i <= length - length1; i++){
				operand1 = operand1.substring(0, 1) + operand1;
			}
		}
		if(operand2.length() < length){
			for(int i = 1; i <= length - length2; i++){
				operand2 = operand2.substring(0, 1) + operand2;
			}
		}
		char[] temp = operand2.toCharArray();
		char[] temp1 = new char[2 * length];
		for(int i = 0; i < length; i++){
			temp1[i] = '0';
		}
		for(int i = length; i < 2 * length; i++){
			temp1[i] = temp[i - length];
		}
		String temp2 = new String(temp1);
		for(int i = 1; i <= length; i++){
			
			if(temp1[2 * length - 1] == '0'){
				temp2 = this.logRightShift(temp2, 1);
				temp1 = temp2.toCharArray();
			}
			else{
				String s = new String(temp1, 0, length);
				String s1 = this.integerAddition(operand1, s, length);
				String s2 = new String(temp1, length - 1, length +1);
				temp2 = s1.substring(1) + s2;
				temp2 = this.logRightShift(temp2, 1);
				temp1 = temp2.toCharArray();
			}
		}
		return temp2;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		String test = "";
		String result = "";
		String test1  = "";
		for(int i = 1; i <= eLength; i++){
			test1 = test1 + "1";
		}
		if(operand1.charAt(0) == operand2.charAt(0)){
			result = "0";
		}
		else{
			result = "1";
		}
		for(int i = 1; i <= eLength + sLength; i++){
			test = test + "0";
		}
		if(operand1.substring(1).equals(test)){
			return "00" + test;
		}
		if(operand2.substring(1).equals(test)){
			return "0" + result + test1 + test.substring(eLength);
		}
		String temp1 = "0000" + operand1.substring(1, 1 + eLength);
		String temp2 = "0000" + operand2.substring(1, 1 + eLength);
		String temp3 = "00000";
		for(int i = 1; i <= eLength - 1; i++){
			temp3 = temp3 + "1";
		}
		String tempOfE1 = this.integerSubtraction(temp1, temp2, eLength + 4).substring(1);
		String tempOfE =  this.integerAddition(tempOfE1, temp3, eLength + 4).substring(5);
		String tempTest = this.integerAddition(tempOfE1, temp3, eLength + 4).substring(1, 5);
		if(!tempTest.equals("0000")){
			result = "1" + result + test1 + test.substring(eLength);
			return result;
		}
		String tempOfS = this.integerDivision2("01" + operand1.substring(1 + eLength), "01" + operand2.substring(1 + eLength), 4 * sLength);
		char[] temp4 = tempOfS.toCharArray();
		int temp5 = 0;
		for(int i = 0; i < temp4.length; i++){
			if(temp4[i] == '1'){
				temp5 = i;
				break;
			}
		}
		if(tempTest.equals("0000") && tempOfE.equals(test.substring(0, eLength))){
			String stemp = tempOfS.substring(temp5);
			int tempTest1 = sLength - stemp.length();
			if(tempTest1 >= 0){
				for(int i = 1; i <= tempTest1; i++){
					stemp = stemp + "0";
				}
			}
			else{
				stemp = stemp.substring(0, sLength);
			}
			result = "0" + result + tempOfE + stemp;
			return result;
		}
		String stemp = tempOfS.substring(temp5 + 1);
		int tempTest1 = sLength - stemp.length();
		if(tempTest1 >= 0){
			for(int i = 1; i <= tempTest1; i++){
				stemp = stemp + "0";
			}
		}
		else{
			stemp = stemp.substring(0, sLength);
		}
		result = "0" + result + tempOfE + stemp;
		return result;
	}
	private String integerDivision2 (String operand1, String operand2, int length) {
		int length1 = operand1.length();
		int length2 = operand2.length();
		if(operand1.length() < length){
			for(int i = 1; i <= length - length1; i++){
				operand1 = operand1.substring(0, 1) + operand1;
			}
		}
		if(operand2.length() < length){
			for(int i = 1; i <= length - length2; i++){
				operand2 = operand2.substring(0, 1) + operand2;
			}
		}
		String remainder = operand1;
		String quotient = "";
		for(int i = 1; i <= length; i++){
			quotient = quotient + "0";
		}
		for(int j = 1; j <= length; j++){
			if(this.integerSubtraction(remainder, quotient, length).substring(1).charAt(0) == '0'){
				remainder = this.integerSubtraction(remainder, operand2, length).substring(1);
				String temp = this.leftShift(remainder + quotient, 1);
				remainder = temp.substring(0, length);
				quotient = temp.substring(length, 2 * length - 1) + "1";
			}
			else{
				String temp = this.leftShift(remainder + quotient, 1);
				remainder = temp.substring(0, length);
				quotient = temp.substring(length, 2 * length - 1) + "0";
			}
		}
		String result = quotient;
		return result;
	}
	
	public static void main(String[] args) {
		ALU alu = new ALU();
		System.out.println(alu.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
//		System.out.println(alu.ariRightShift("1011", 3));
//		String s = "11.375";
//		int m = s.indexOf(".");
//		String s1 = s.split(".")[0];
//		System.out.println(m);
	}
}
