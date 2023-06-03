package DB2023;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainFrame extends JFrame{
	public MainFrame() {
		setSize(1000,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("DB2023Team04");
		setLayout(new BorderLayout());
		
		//header 부분 (홈 화면으로 되돌아가는 버튼)
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout());
		header.setBorder(BorderFactory.createEmptyBorder(10 , 0 , 0 , 0));
		
		JButton homebutton = new JButton("도비");
		homebutton.setPreferredSize(new Dimension(900,70));
		homebutton.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		header.add(homebutton);
		
		
		//sidebar 부분 (로그인, 마이페이지, 신권신청)
		JPanel sidebar = new JPanel();
		sidebar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		sidebar.setBorder(BorderFactory.createEmptyBorder(5 , 45 , 0 , 5));	
		sidebar.setPreferredSize(new Dimension(200,600));
		JTextField id = new JTextField(15);
		id.setText("ID");
		JTextField passwd = new JTextField(15);
		passwd.setText("Password");
		sidebar.add(id);
		sidebar.add(passwd);
		sideButton(sidebar, "Login");
		sideButton(sidebar, "회원 가입");
		sideButton(sidebar, "마이 페이지");
		sideButton(sidebar, "신권 신청하기");
		
		add(header, BorderLayout.PAGE_START);
		add(sidebar, BorderLayout.LINE_START);
		setVisible(true);
	}
	
	//sidebar 버튼 생성 메서드
	public void sideButton(JPanel panel, String text) {
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(150,50));
		panel.add(button);
	}
}

class HomeFrame extends MainFrame{
	JRadioButton RBCallnum = new JRadioButton("분류 기호");
	JRadioButton RBAuthor = new JRadioButton("작가");
	JRadioButton RBTitle = new JRadioButton("제목", true);
	ButtonGroup radioGroup = new ButtonGroup();
	
	public HomeFrame() {
		super();
		setTitle("DB2023Team04_Home");
		
		//body 부분(검색, 추천 도서)
		JPanel body = new JPanel();
		body.setLayout(new BorderLayout());
		body.setBorder(BorderFactory.createEmptyBorder(30 , 5 , 0 , 45));
		
		//검색
		JPanel searchArea = new JPanel();		
		searchArea.setLayout(new BorderLayout(0,15));		
		JLabel searchLabel = new JLabel("도서 검색");
		searchLabel.setHorizontalAlignment(JLabel.CENTER);
		searchLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		searchArea.add(searchLabel, BorderLayout.NORTH);
		
		JTextField searchfield = new JTextField(30);
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new SearchListener(searchfield));
		JPanel search = new JPanel();
		search.add(searchfield);
		search.add(searchButton);
		searchArea.add(search, BorderLayout.CENTER);
		
		JPanel searchOption = new JPanel();
		
		radioGroup.add(RBCallnum);
		radioGroup.add(RBAuthor);
		radioGroup.add(RBTitle);
		searchOption.add(RBCallnum);
		searchOption.add(RBAuthor);
		searchOption.add(RBTitle);
		searchArea.add(searchOption, BorderLayout.SOUTH);
		
		
		//추천도서
		JPanel recommend = new JPanel();
		recommend.setLayout(new FlowLayout(FlowLayout.CENTER,0,5));
		JLabel recomLabel = new JLabel("추천 도서");
		recomLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		recomLabel.setPreferredSize(new Dimension(700,50));
		recommend.add(recomLabel);
		recommendButton(recommend, "책1");
		recommendButton(recommend, "책2");
		recommendButton(recommend, "책3");
		recommendButton(recommend, "책4");
		recommendButton(recommend, "책5");
		
		body.add(searchArea, BorderLayout.NORTH);
		body.add(recommend, BorderLayout.CENTER);
		
		super.add(body, BorderLayout.CENTER);
	}
	
	public void recommendButton(JPanel panel, String string) {
		JButton button = new JButton(string);
		button.setPreferredSize(new Dimension(700,50));
		panel.add(button);
	}


	class SearchListener implements ActionListener{
		JTextField text;
		String target;
		
		public SearchListener(JTextField text) {
			this.text = text;
		}
	
		public void actionPerformed(ActionEvent e) {		
			if(RBCallnum.isSelected())
				target = "Call_num";
			else if (RBAuthor.isSelected())
				target = "Author";
			else if (RBTitle.isSelected())
				target = "Book_Title";
			DB2023_JDBC_Home.DB2023_JDBC_search(target, text.getText());
			text.setText("");
			System.out.println("actionPerformed: Search " + target);
		}
	}
}

public class Home {

	public static void main(String[] args) {
		HomeFrame home = new HomeFrame();
	}

}