package com.pcwk.ehr.library.test;
import java.io.*;


public class FileMerge {

	static int found = 0;//특정 단어 count
	static String fileName = "PCWK_";//특정 단어 count
	

	
	
	public static void findInFile(File dir) throws IOException {
		File[] files = dir.listFiles();//file, directory
		//파일쓰기
		FileWriter  fw=new FileWriter(fileName);
		BufferedWriter  bw=new BufferedWriter(fw);
		
		for(int i=0;i<files.length;i++) {

			if(files[i].isDirectory()==true) {
				findInFile(files[i]);
			}else {
				//*.java파일 만 읽는다.
				
				String fileName = files[i].getName();//파일명
				int idx = fileName.lastIndexOf(".");
				String ext = fileName.substring(idx+1);
				
				if(!"sql".equalsIgnoreCase(ext)) {
					continue;
				}
				
				//파일읽기
				FileReader  fr=new FileReader(files[i]);
				BufferedReader  br=new BufferedReader(fr);
				

				
				System.out.println("파일이름:"+FileMerge.fileName);
				
				
				String data = "";//한줄 데이터
				int lineNum = 0;//라인 넘버
				
				
				while( ( data=br.readLine())!=null ) {
					lineNum++;
					System.out.println(lineNum+"\t"+data);
					
					bw.append(data+"\n");
					
				}
				bw.append("--*********************************************************--\n");
				
				System.out.println("------------------------------------------");
				
				br.close();

			}
			
		}//--for i
		
		bw.close();
	}
	
	public static void main(String[] args) {
		
		//param : 디렉토리, 검색어
		if(args.length !=1) {
			System.out.println("디렉토리  검색어를 입력 하세요.");
			System.exit(0);
		}

		
		File   dir=new File(args[0]);
		fileName += dir.getName();//마지막 디렉토리명
		fileName += ".txt";
		
		
		if(!dir.exists() || !dir.isDirectory()) {
			System.out.println("유효하지 않은 디렉토리 입니다.");
			System.exit(0);			
		}
		
		try {
			findInFile(dir);
		} catch (IOException e) {
			System.out.println("======================");
			System.out.println("=IOException="+e.getMessage());
			System.out.println("======================");
			e.printStackTrace();
		}

	}

}
