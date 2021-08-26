package ht.ordermanage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ht.Main;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class OrderManage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderManage frame = new OrderManage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OrderManage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton button = new JButton("回主頁");
		button.setFont(new Font("新細明體", Font.PLAIN, 26));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderManage.this.dispose();
				new Main();
			}
		});
		
		JButton btnNewButton = new JButton("新增訂單");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddOrder ID = new AddOrder(); 
				ID.setModal(true); 
				ID.setVisible(true);
			}
		});
		contentPane.setLayout(new GridLayout(0, 1));
		btnNewButton.setFont(new Font("新細明體", Font.PLAIN, 26));
		contentPane.add(btnNewButton);
		
		JButton btnListButton = new JButton("列出訂單");
		btnListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListOrder ID = new ListOrder(); 
				ID.setModal(true); 
				ID.setVisible(true);
			}
		});
		contentPane.setLayout(new GridLayout(0, 1));
		btnListButton.setFont(new Font("新細明體", Font.PLAIN, 26));
		contentPane.add(btnListButton);
		contentPane.add(button);
		this.setVisible(true);  
	}

}
