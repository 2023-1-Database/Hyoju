package DB2023;
//신권 신청 페이지 출력하는 곳

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;


public class DB2023Team04_NewBookList extends JFrame{
        public String id;//사용자 아이디
        
    	Object[][] data = new Object[][] {};//창에서 출력할 신권 신청 테이블 정보
    	public ResultSet rset;// 테이블 객체
    	DefaultTableModel Model = new DefaultTableModel(data,new String[] {"책 이름","저자","출판사","신청 상태","신청한 회원"});
    	
    	//신권 신청 창 ,  닫는 버튼
    	JTable ltable=new JTable(Model);
    	JScrollPane jScrollPane=new JScrollPane(ltable);
    	JButton close=new JButton("close");

        public DB2023Team04_NewBookList(String uid) {
        	id= uid;//id에 사용자 아이디 저장
        	setTitle("신권 신청");//창 이름
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	//패널
        	Container contentPane = getContentPane();
        	contentPane.setLayout(new BorderLayout(0,200));
        	JPanel Panel1 = new JPanel();
        	JPanel Panel2= new JPanel();
        	JPanel Panel3 = new JPanel();
        	
        	//라벨
        	JLabel newLabel = new JLabel("신권 신청 목록");
        	Font labelFont = newLabel.getFont();
        	newLabel.setFont(new Font(labelFont.getFontName(), Font.BOLD, 32)); // 폰트 크기를 24로 설정
        	
        	//신권 신청하기 올리기 버튼
        	JButton newReq= new JButton("신권 신청하기");
        	
        	//검색창이랑 버튼 사이즈
        	newReq.setPreferredSize(new Dimension(200, 30));
        	
        	//패널에 라벨 , 버튼 붙이기
        	Panel1.add(newLabel);
        	Panel3.add(newReq);
        	Panel3.add(close);
  
        	DB2023Team04_JDBC db= new DB2023Team04_JDBC(); 
        	//신권 신청 목록 
    		try {//창에서 분실물 물품 리스트를 출력하기 위한 쿼리
    			
    			
    			Statement stmt = db.connection.createStatement();
    			rset = stmt.executeQuery("select Book_Title, Author,Publisher,Req_Status, Member_ID from DB2023_new_req");//모든 분실물 목록 출력
    			
    			String[] columns=new String[] {"책 이름","저자","출판사","신청 상태","신청한 회원"};
    			Object[][] data=new Object[][] {};
    					
    					
    			while(rset.next()) {//테이블에 신권 신청 리스트 넣기
   		        Model.insertRow(0, new Object[] {rset.getString("Book_Title"),rset.getString("Author"),rset.getString("Publisher"),rset.getString("Req_Status"),rset.getString("Member_ID")," "});
    			}

    			JScrollPane scroll=new JScrollPane(ltable);
    			scroll.setPreferredSize(new Dimension(800,200));
    			
    			Panel2.add(scroll);
    			
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    		//패널 위치
    		contentPane.add(Panel1,BorderLayout.NORTH);
        	contentPane.add(Panel2,BorderLayout.CENTER);
            contentPane.add(Panel3,BorderLayout.SOUTH);
            
            //버튼 리스너
            ButtonListener reg=new ButtonListener();
            //무료나눔 글쓰기 버튼
            newReq.addActionListener(reg);
        	
        	// 액션 리스너
        	
        	// 닫기
        	close.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			JButton bt=(JButton)e.getSource();
        			if(bt.getText().equals("close")){
        				//new home(uid);//새로운 home창 생성
        				dispose();//원래 창 닫기
        				}
        	
        		}
        	});


        	setSize(1500,800);
        	setVisible(true);
        }
      
 class ButtonListener implements ActionListener{//버튼 리스너
    		public void actionPerformed(ActionEvent e) {
    			JButton bt=(JButton)e.getSource();
    			
    			if(bt.getText().equals("신권 신청하기")) {
    				System.out.println("신권 신청하기");
    				// 분실물 글 올리는창 이동
    				new DB2023Team04_RequestNewBook(id);//신권 신청 버튼 누르면 새로운 글 올리는 창 생성
    				dispose();//원래 창 닫기
    			}
            }
    	}
  

}

