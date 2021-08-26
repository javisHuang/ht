package ht.ordermanage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import ht.Tools;
import ht.form.OrderFormForm;
import ht.form.OrderManageForm;
import ht.service.OrderService;

import java.awt.Font;

public class AddOrder extends JDialog {

	private final JPanel addressPanel = new JPanel();
	private JDatePickerImpl datepick;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddOrder dialog = new AddOrder();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddOrder() {
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());

		Border border = addressPanel.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		addressPanel.setBorder(new CompoundBorder(border, margin));

		GridBagLayout panelGridBagLayout = new GridBagLayout();
		panelGridBagLayout.columnWidths = new int[] { 86, 86, 0 };
		panelGridBagLayout.rowHeights = new int[] { 20, 20, 20, 20, 20, 0 };
		panelGridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		addressPanel.setLayout(panelGridBagLayout);
		
		datepick = addLabelAndDate("日期",0, addressPanel);
		addLabelAndTextField("遺體接運:", 1, addressPanel,"A1");
		addLabelAndTextField("安靈服務:", 2, addressPanel,"A2");
		addLabelAndTextField("擇日:", 3, addressPanel,"A3");
		addLabelAndTextField("服務諮詢人員:", 4, addressPanel,"A4");
		addLabelAndTextField("訂禮堂及火化爐:", 5, addressPanel,"A5");

		getContentPane().add(addressPanel, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date datee = (Date) datepick.getModel().getValue();
				Long orderid = Tools.getOrderId();
				OrderManageForm ordermform = new OrderManageForm();
				ordermform.setOrderId(orderid);
				ordermform.setDatetime(new SimpleDateFormat("yyyyMMdd").format(datee));
				ordermform.setCreatetime(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
				
				ArrayList<OrderFormForm> orderoforms = new ArrayList<OrderFormForm>();
				orderoforms.add(getOrderForm(orderid,"A1"));
				orderoforms.add(getOrderForm(orderid,"A2"));
				orderoforms.add(getOrderForm(orderid,"A3"));
				orderoforms.add(getOrderForm(orderid,"A4"));
				orderoforms.add(getOrderForm(orderid,"A5"));
				
				OrderService.getInstance().addOrder(ordermform, orderoforms);
				JOptionPane.showMessageDialog(AddOrder.this, "新增成功");
				AddOrder.this.dispose();
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddOrder.this.dispose();
			}
		});
		buttonPane.add(cancelButton);
	}

	private void addLabelAndTextField(String labelText, int yPos, Container containingPanel,String key) {

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("新細明體", Font.PLAIN, 26));
		GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
		gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
		gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintForLabel.gridx = 0;
		gridBagConstraintForLabel.gridy = yPos;
		containingPanel.add(label, gridBagConstraintForLabel);

		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    JFormattedTextField textField = new JFormattedTextField(formatter);
		textField.setFont(new Font("新細明體", Font.PLAIN, 26));
		GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
		gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
		gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
		gridBagConstraintForTextField.gridx = 1;
		gridBagConstraintForTextField.gridy = yPos;
		containingPanel.add(textField, gridBagConstraintForTextField);
		textField.setColumns(10);
		textField.putClientProperty("id",key);
	}
	
	private JDatePickerImpl addLabelAndDate(String labelText, int yPos, Container containingPanel) {
		JLabel label = new JLabel(labelText);
		label.setFont(new Font("新細明體", Font.PLAIN, 26));
		GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
		gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
		gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintForLabel.gridx = 0;
		gridBagConstraintForLabel.gridy = yPos;
		containingPanel.add(label, gridBagConstraintForLabel);

		GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
		gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
		gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
		gridBagConstraintForTextField.gridx = 1;
		gridBagConstraintForTextField.gridy = yPos;
		
		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		//model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		JFormattedTextField textField = datePicker.getJFormattedTextField();
		textField.setFont(new Font("新細明體", Font.PLAIN, 20));
		containingPanel.add(datePicker, gridBagConstraintForTextField);
		return datePicker;
	}

	private OrderFormForm getOrderForm(Long orderid,String key) {
		OrderFormForm orderFormForm = new OrderFormForm();
		orderFormForm.setOrderId(orderid);
		orderFormForm.setKey(key);
        JTextField jt = (JTextField) Tools.findComById(addressPanel, key);
		orderFormForm.setValue(jt.getText());
		return orderFormForm;
	}

	
	public class DateLabelFormatter extends AbstractFormatter {

	    private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}
}
