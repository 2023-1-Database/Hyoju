package DB2023;
//신권 신청 페이지
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class DB2023Team04_RequestNewBook extends JFrame {
	public String id;
	public DB2023Team04_RequestNewBook(String uid) {
		id = uid;
		setTitle("신권 신청하기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
		JPanel northPanel = new JPanel();
		JPanel center1Panel = new JPanel();
		JPanel center2Panel = new JPanel();
		//버튼 붙을 패널
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// 라벨
		JLabel titleL = new JLabel("책 제목");
		JLabel authorL = new JLabel(" 저자 ");
		JLabel publisherL = new JLabel("출판사");
		
		
		// 텍스트필드
		JTextField title = new JTextField();
		JTextField author = new JTextField();
		JTextField publisher = new JTextField();
		
		//신권 신청 버튼
		JButton newbtn = new JButton("신권 신청하기");
		JButton cancel = new JButton("취소");
		
		// 텍스트 상자 크기 조정
		title.setPreferredSize(new Dimension(200, 30));
		author.setPreferredSize(new Dimension(200, 30));
		publisher.setPreferredSize(new Dimension(200, 30));
		
		// 패널에 라벨, 텍스트 필드 붙이기
		northPanel.add(titleL);
		northPanel.add(title);
		center1Panel.add(authorL);
		center1Panel.add(author);
		center2Panel.add(publisherL);
		center2Panel.add(publisher);
		bottomPanel.add(newbtn);
		bottomPanel.add(cancel);
		
		
		// 컨텐트팬에 패널, 버튼 붙이기
		contentPane.add(northPanel);
		contentPane.add(center1Panel);
		contentPane.add(center2Panel);
		contentPane.add(bottomPanel);
		
		
		// insert 하기 위한 쿼리문
		String sql = "insert into DB2023_new_req values (?,?,?,?,?,?)";
		//Database 클래스
		DB2023Team04_JDBC db= new DB2023Team04_JDBC(); 
		
		// 액션 리스너
		newbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
					// 확인 다이얼로그 창
					int result = JOptionPane.showConfirmDialog(null, "이대로 신청하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.CLOSED_OPTION) 
						; // 사용자가 "예", "아니오"의 선택 없이 다이얼로그 창을 닫은 경우
					else if(result == JOptionPane.YES_OPTION) {
						try { // ps에 값 저장
							db.connection.setAutoCommit(false);
							PreparedStatement ps = db.connection.prepareStatement(sql);
							Statement stmt = db.connection.createStatement();
							ResultSet rset = stmt.executeQuery("select max(Req_num) from DB2023_new_req");
							while(rset.next()) {
								ps.setInt(1, (rset.getInt(1)+1));
							}
							ps.setString(2, title.getText() );
							ps.setString(3, author.getText());
							ps.setString(4, publisher.getText());
							ps.setString(5, "신청접수");
							ps.setString(6,id );
							ps.executeUpdate();
							db.connection.commit();
							System.out.println("등록 완료");
							db.connection.setAutoCommit(true);
							System.out.println("setAutoCommit = true");
						}catch(SQLException e1) {
							e1.printStackTrace();
							try {
								if(db.connection!=null)
									db.connection.rollback();
							}catch(SQLException se) {
								se.printStackTrace();
							}
						}
						new DB2023Team04_NewBookList(id);
						dispose();
					}// 사용자가 "예"를 선택한 경우
					else 
						;// 사용자가 "아니오"를 선택한 경우
							
			}
					
		});
		// 취소 버튼에 리스너	
		buttonListener buli = new buttonListener();
		cancel.addActionListener(buli); 
			
		setSize(500,500);
		setVisible(true);
	}
	// 사용자가 취소 버튼 누를 시
	private class buttonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton b = (JButton)e.getSource();
			if(b.getText().equals("취소")){
				System.out.println("신권 신청을 취소합니다");
				new DB2023Team04_NewBookList(id);
				dispose();
			}

		}
		
	}
}

