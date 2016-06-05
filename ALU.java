/**
 * ģ��ALU���������͸���������������
 * @author 151250134_ ��ԭ
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
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
			//ȡ��
			for(int i = 0; i < length; i++){
				resultOfTemp[i] = 1 - resultOfTemp[i];
			}
			int c = 1;                              //ȡ����1ʱ�Ľ�λ
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
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		int length = 1 + eLength + sLength;
		int eMax = (int)Math.pow(2, eLength - 1) - 1;                   //������ָ��
		double max = (2 - Math.pow(2, -sLength)) * Math.pow(2, eMax);   //����ܱ�ʾ��������
		double min = Math.pow(2, -eMax + 1);                            //����ܱ�ʾ����С����
		
		int[] resultOfTemp = new int[length];
		if(number.startsWith("-")){
			resultOfTemp[0] = 1;
			number = number.substring(1);
		}
		//��������
		if(Double.parseDouble(number) > max){
			for(int i = 1; i <= eLength + 1; i++){
				resultOfTemp[i] = 1;
			}
		}
		//�����
		else if(Double.parseDouble(number) >= min){
			int dot = number.indexOf(".");
			double part1 = Double.parseDouble(number.substring(0, dot));    //��������
			double part2 = Double.parseDouble(number) - part1;              //С������
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
			int count = length1 - m - 1;                       //С�������ƵĴ���
			int e = count + eMax;                              //ָ�����ֵĴ�С
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
		//�ǹ����
		else{
			int dot = number.indexOf(".");
			double part1 = Double.parseDouble(number.substring(0, dot));    //��������
			double part2 = Double.parseDouble(number) - part1;              //С������
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
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
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
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
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
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
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
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		for(int i = 1; i <= n; i++){
			operand = operand.substring(1) + "0";
		}
		return operand;
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		for(int i = 1; i <= n; i++){
			operand = "0" + operand.substring(0, operand.length() - 1);
		}
		return operand;
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		for(int i = 1; i <= n; i++){
			operand = operand.substring(0,1) + operand.substring(0, operand.length() - 1);
		}
		return operand;
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
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
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
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
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
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
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", '0', 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
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
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return this.adder(operand1, operand2, '0', length);
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		operand2 = this.negation(operand2);
		return this.adder(operand1, operand2, '1', length);
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
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
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
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
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
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
			if(result.charAt(length + 1 - max) == '1'){
				if(max == length){
					result = "1" + operand1.substring(0, 1) + "0" + result.substring(length + 2 - max);
				}
				else{
					result = "1" + operand1.substring(0, 1) + result.substring(1, length - max) + "0" + result.substring(length + 2 - max);
				}
			}
			else{
				result = "0" + operand1.substring(0, 1) + result.substring(1);
			}
		}
		else{
			result = this.integerSubtraction(operand1temp, operand2temp, length);
			int max = length1;
			if(length1 < length2){
				max = length2;
			}
			if(result.charAt(length + 1 - max) == '1'){
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
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
		if(this.isZero(result.substring(2 + eLength), sLength)){
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
	//�жϲ������Ƿ�Ϊ0
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
		//�ж������������Ĵ�С,����ֵΪ0ʱ��ʾ��ȣ�����1ʱ��ʾoperand1�Դ󣬷���2ʱ��ʾoperand2�Դ�
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
		//��1��
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
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
		System.out.println(alu.floatMultiplication("00111110111000000000000000000000", "00111111000000000000000000000000", 8, 23));
//		System.out.println(alu.ariRightShift("1011", 3));
//		String s = "11.375";
//		int m = s.indexOf(".");
//		String s1 = s.split(".")[0];
//		System.out.println(m);
	}
}
