package bzb.gwt.planner.client.panels;

import java.util.Date;

import bzb.gwt.planner.client.CUser;
import bzb.gwt.planner.client.OpenIdService;
import bzb.gwt.planner.client.OpenIdServiceAsync;
import bzb.gwt.planner.client.Planner;
import bzb.gwt.planner.client.SaveService;
import bzb.gwt.planner.client.SaveServiceAsync;
import bzb.gwt.planner.client.Planner.State;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginPanel extends FlowPanel {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private static final OpenIdServiceAsync openidService = GWT.create(OpenIdService.class);
	private static final SaveServiceAsync saveService = GWT.create(SaveService.class);
	
	private static final int AGEBOX_YEAR_ZERO = 1900;
	private static final int AVERAGE_AGE = 27;
	private static final int THIS_YEAR = Integer.parseInt(DateTimeFormat.getFormat("y").format(new Date()));

	public LoginPanel() {
		if (Window.Location.getParameter("state") != null && Window.Location.getParameter("state").equals("auth")) {
			openidService.verifyAuth(Window.Location.getHref(),
					new AsyncCallback<CUser>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							caught.printStackTrace();
							System.out
									.println("Remote Procedure Call - Failure");
						}

						public void onSuccess(CUser user) {
							Planner.setUser(user);
							
							final VerticalPanel vp = new VerticalPanel();
					
							saveService.checkUser(Planner.getUser().getUserAuth(), new AsyncCallback<String>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									caught.printStackTrace();
									System.out.println("Remote Procedure Call - Failure");
								}

								public void onSuccess(String encodedKey) {
									if (encodedKey != null) {
										Button done = new Button();
									    done.setText("Done");
									    done.addClickHandler(new ClickHandler() {
			
											public void onClick(ClickEvent event) {
												Planner.updateContent(State.TRIPS);
											}
									    	
									    });
									    vp.add(done);
									} else {
										HorizontalPanel hpAge = new HorizontalPanel();
										hpAge.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hpAge.add(new HTML("Year of birth"));
										final ListBox ageBox = new ListBox();
										for (int y = THIS_YEAR; y >= AGEBOX_YEAR_ZERO; y--) {
											ageBox.addItem(String.valueOf(y));
										}
										if (Planner.getUser().getAge() >= AGEBOX_YEAR_ZERO) {
											ageBox.setSelectedIndex(Planner.getUser().getAge() - AGEBOX_YEAR_ZERO);
										} else {
											ageBox.setSelectedIndex(AVERAGE_AGE);
										}
										hpAge.add(ageBox);
										vp.add(hpAge);
										
										HorizontalPanel hpEmail = new HorizontalPanel();
										hpEmail.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hpEmail.add(new HTML("Email address"));
										TextBox emailBox = new TextBox();
										if (Planner.getUser().getUsername() != null) {
											emailBox.setVisibleLength(Planner.getUser().getUsername().length() + 5);
											emailBox.setText(Planner.getUser().getUsername());
											emailBox.setEnabled(false);
										}
										hpEmail.add(emailBox);
										vp.add(hpEmail);
										
										HorizontalPanel hpFullname = new HorizontalPanel();
										hpFullname.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hpFullname.add(new HTML("Full name"));
										final TextBox fullnameBox = new TextBox();
										if (Planner.getUser().getFullName() != null) {
											fullnameBox.setText(Planner.getUser().getFullName());
										}
										hpFullname.add(fullnameBox);
										vp.add(hpFullname);
										
										HorizontalPanel hpGender = new HorizontalPanel();
										hpGender.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hpGender.add(new HTML("Gender"));
										final ListBox genderBox = new ListBox();
										genderBox.addItem("Male");
										genderBox.addItem("Female");
										if (Planner.getUser().getMale()) {
											genderBox.setSelectedIndex(0);
										} else {
											genderBox.setSelectedIndex(1);
										}
										hpGender.add(genderBox);
										vp.add(hpGender);
										
										HorizontalPanel hpCountry = new HorizontalPanel();
										hpCountry.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hpCountry.add(new HTML("Country of residence"));
										final ListBox homeCountryBox = new ListBox();
									    homeCountryBox.addItem("Afghanistan");
									    homeCountryBox.addItem("Albania");
									    homeCountryBox.addItem("Algeria");
									    homeCountryBox.addItem("American Samoa");
									    homeCountryBox.addItem("Andorra");
									    homeCountryBox.addItem("Angola");
									    homeCountryBox.addItem("Anguilla");
									    homeCountryBox.addItem("Antarctica");
									    homeCountryBox.addItem("Antigua And Barbuda");
									    homeCountryBox.addItem("Argentina");
									    homeCountryBox.addItem("Armenia");
									    homeCountryBox.addItem("Aruba");
									    homeCountryBox.addItem("Australia");
									    homeCountryBox.addItem("Austria");
									    homeCountryBox.addItem("Azerbaijan");
									    homeCountryBox.addItem("Bahamas");
									    homeCountryBox.addItem("Bahrain");
									    homeCountryBox.addItem("Bangladesh");
									    homeCountryBox.addItem("Barbados");
									    homeCountryBox.addItem("Belarus");
									    homeCountryBox.addItem("Belgium");
									    homeCountryBox.addItem("Belize");
									    homeCountryBox.addItem("Benin");
									    homeCountryBox.addItem("Bermuda");
									    homeCountryBox.addItem("Bhutan");
									    homeCountryBox.addItem("Bolivia");
									    homeCountryBox.addItem("Bosnia And Herzegovina");
									    homeCountryBox.addItem("Botswana");
									    homeCountryBox.addItem("Bouvet Island");
									    homeCountryBox.addItem("Brazil");
									    homeCountryBox.addItem("British Indian Ocean Territory");
									    homeCountryBox.addItem("Brunei Darussalam");
									    homeCountryBox.addItem("Bulgaria");
									    homeCountryBox.addItem("Burkina Faso");
									    homeCountryBox.addItem("Burundi");
									    homeCountryBox.addItem("Cambodia");
									    homeCountryBox.addItem("Cameroon");
									    homeCountryBox.addItem("Canada");
									    homeCountryBox.addItem("Cape Verde");
									    homeCountryBox.addItem("Cayman Islands");
									    homeCountryBox.addItem("Central African Republic");
									    homeCountryBox.addItem("Chad");
									    homeCountryBox.addItem("Chile");
									    homeCountryBox.addItem("China");
									    homeCountryBox.addItem("Christmas Island");
									    homeCountryBox.addItem("Cocos (Keeling) Islands");
									    homeCountryBox.addItem("Colombia");
									    homeCountryBox.addItem("Comoros");
									    homeCountryBox.addItem("Congo, The Democratic Republic Of The");
									    homeCountryBox.addItem("Congo");
									    homeCountryBox.addItem("Cook Islands");
									    homeCountryBox.addItem("Costa Rica");
									    homeCountryBox.addItem("Cote D''ivoire");
									    homeCountryBox.addItem("Croatia");
									    homeCountryBox.addItem("Cuba");
									    homeCountryBox.addItem("Cyprus");
									    homeCountryBox.addItem("Czech Republic");
									    homeCountryBox.addItem("Denmark");
									    homeCountryBox.addItem("Djibouti");
									    homeCountryBox.addItem("Dominica");
									    homeCountryBox.addItem("Dominican Republic");
									    homeCountryBox.addItem("East Timor");
									    homeCountryBox.addItem("Ecuador");
									    homeCountryBox.addItem("Egypt");
									    homeCountryBox.addItem("El Salvador");
									    homeCountryBox.addItem("Equatorial Guinea");
									    homeCountryBox.addItem("Eritrea");
									    homeCountryBox.addItem("Estonia");
									    homeCountryBox.addItem("Ethiopia");
									    homeCountryBox.addItem("Falkland Islands (Malvinas)");
									    homeCountryBox.addItem("Faroe Islands");
									    homeCountryBox.addItem("Fiji");
									    homeCountryBox.addItem("Finland");
									    homeCountryBox.addItem("France");
									    homeCountryBox.addItem("French Guiana");
									    homeCountryBox.addItem("French Polynesia");
									    homeCountryBox.addItem("French Southern Territories");
									    homeCountryBox.addItem("Gabon");
									    homeCountryBox.addItem("Gambia");
									    homeCountryBox.addItem("Georgia");
									    homeCountryBox.addItem("Germany");
									    homeCountryBox.addItem("Ghana");
									    homeCountryBox.addItem("Gibraltar");
									    homeCountryBox.addItem("Greece");
									    homeCountryBox.addItem("Greenland");
									    homeCountryBox.addItem("Grenada");
									    homeCountryBox.addItem("Guadeloupe");
									    homeCountryBox.addItem("Guam");
									    homeCountryBox.addItem("Guatemala");
									    homeCountryBox.addItem("Guinea-Bissau");
									    homeCountryBox.addItem("Guinea");
									    homeCountryBox.addItem("Guyana");
									    homeCountryBox.addItem("Haiti");
									    homeCountryBox.addItem("Heard Island And Mcdonald Islands");
									    homeCountryBox.addItem("Holy See (Vatican City State)");
									    homeCountryBox.addItem("Honduras");
									    homeCountryBox.addItem("Hong Kong");
									    homeCountryBox.addItem("Hungary");
									    homeCountryBox.addItem("Iceland");
									    homeCountryBox.addItem("India");
									    homeCountryBox.addItem("Indonesia");
									    homeCountryBox.addItem("Iran, Islamic Republic Of");
									    homeCountryBox.addItem("Iraq");
									    homeCountryBox.addItem("Ireland");
									    homeCountryBox.addItem("Israel");
									    homeCountryBox.addItem("Italy");
									    homeCountryBox.addItem("Jamaica");
									    homeCountryBox.addItem("Japan");
									    homeCountryBox.addItem("Jordan");
									    homeCountryBox.addItem("Kazakstan");
									    homeCountryBox.addItem("Kenya");
									    homeCountryBox.addItem("Kiribati");
									    homeCountryBox.addItem("Korea, Democratic People''s Republic Of");
									    homeCountryBox.addItem("Korea, Republic Of");
									    homeCountryBox.addItem("Kuwait");
									    homeCountryBox.addItem("Kyrgyzstan");
									    homeCountryBox.addItem("Lao People''s Democratic Republic");
									    homeCountryBox.addItem("Latvia");
									    homeCountryBox.addItem("Lebanon");
									    homeCountryBox.addItem("Lesotho");
									    homeCountryBox.addItem("Liberia");
									    homeCountryBox.addItem("Libyan Arab Jamahiriya");
									    homeCountryBox.addItem("Liechtenstein");
									    homeCountryBox.addItem("Lithuania");
									    homeCountryBox.addItem("Luxembourg");
									    homeCountryBox.addItem("Macau");
									    homeCountryBox.addItem("Macedonia, The Former Yugoslav Republic Of");
									    homeCountryBox.addItem("Madagascar");
									    homeCountryBox.addItem("Malawi");
									    homeCountryBox.addItem("Malaysia");
									    homeCountryBox.addItem("Maldives");
									    homeCountryBox.addItem("Mali");
									    homeCountryBox.addItem("Malta");
									    homeCountryBox.addItem("Marshall Islands");
									    homeCountryBox.addItem("Martinique");
									    homeCountryBox.addItem("Mauritania");
									    homeCountryBox.addItem("Mauritius");
									    homeCountryBox.addItem("Mayotte");
									    homeCountryBox.addItem("Mexico");
									    homeCountryBox.addItem("Micronesia, Federated States Of");
									    homeCountryBox.addItem("Moldova, Republic Of");
									    homeCountryBox.addItem("Monaco");
									    homeCountryBox.addItem("Mongolia");
									    homeCountryBox.addItem("Montserrat");
									    homeCountryBox.addItem("Morocco");
									    homeCountryBox.addItem("Mozambique");
									    homeCountryBox.addItem("Myanmar");
									    homeCountryBox.addItem("Namibia");
									    homeCountryBox.addItem("Nauru");
									    homeCountryBox.addItem("Nepal");
									    homeCountryBox.addItem("Netherlands Antilles");
									    homeCountryBox.addItem("Netherlands");
									    homeCountryBox.addItem("New Caledonia");
									    homeCountryBox.addItem("New Zealand");
									    homeCountryBox.addItem("Nicaragua");
									    homeCountryBox.addItem("Niger");
									    homeCountryBox.addItem("Nigeria");
									    homeCountryBox.addItem("Niue");
									    homeCountryBox.addItem("Norfolk Island");
									    homeCountryBox.addItem("Northern Mariana Islands");
									    homeCountryBox.addItem("Norway");
									    homeCountryBox.addItem("Oman");
									    homeCountryBox.addItem("Pakistan");
									    homeCountryBox.addItem("Palau");
									    homeCountryBox.addItem("Palestinian Territory, Occupied");
									    homeCountryBox.addItem("Panama");
									    homeCountryBox.addItem("Papua New Guinea");
									    homeCountryBox.addItem("Paraguay");
									    homeCountryBox.addItem("Peru");
									    homeCountryBox.addItem("Philippines");
									    homeCountryBox.addItem("Pitcairn");
									    homeCountryBox.addItem("Poland");
									    homeCountryBox.addItem("Portugal");
									    homeCountryBox.addItem("Puerto Rico");
									    homeCountryBox.addItem("Qatar");
									    homeCountryBox.addItem("Reunion");
									    homeCountryBox.addItem("Romania");
									    homeCountryBox.addItem("Russian Federation");
									    homeCountryBox.addItem("Rwanda");
									    homeCountryBox.addItem("Saint Helena");
									    homeCountryBox.addItem("Saint Kitts And Nevis");
									    homeCountryBox.addItem("Saint Lucia");
									    homeCountryBox.addItem("Saint Pierre And Miquelon");
									    homeCountryBox.addItem("Saint Vincent And The Grenadines");
									    homeCountryBox.addItem("Samoa");
									    homeCountryBox.addItem("San Marino");
									    homeCountryBox.addItem("Sao Tome And Principe");
									    homeCountryBox.addItem("Saudi Arabia");
									    homeCountryBox.addItem("Senegal");
									    homeCountryBox.addItem("Seychelles");
									    homeCountryBox.addItem("Sierra Leone");
									    homeCountryBox.addItem("Singapore");
									    homeCountryBox.addItem("Slovakia");
									    homeCountryBox.addItem("Slovenia");
									    homeCountryBox.addItem("Solomon Islands");
									    homeCountryBox.addItem("Somalia");
									    homeCountryBox.addItem("South Africa");
									    homeCountryBox.addItem("South Georgia And The South Sandwich Islands");
									    homeCountryBox.addItem("Spain");
									    homeCountryBox.addItem("Sri Lanka");
									    homeCountryBox.addItem("Sudan");
									    homeCountryBox.addItem("Suriname");
									    homeCountryBox.addItem("Svalbard And Jan Mayen");
									    homeCountryBox.addItem("Swaziland");
									    homeCountryBox.addItem("Sweden");
									    homeCountryBox.addItem("Switzerland");
									    homeCountryBox.addItem("Syrian Arab Republic");
									    homeCountryBox.addItem("Taiwan, Province Of China");
									    homeCountryBox.addItem("Tajikistan");
									    homeCountryBox.addItem("Tanzania, United Republic Of");
									    homeCountryBox.addItem("Thailand");
									    homeCountryBox.addItem("Togo");
									    homeCountryBox.addItem("Tokelau");
									    homeCountryBox.addItem("Tonga");
									    homeCountryBox.addItem("Trinidad And Tobago");
									    homeCountryBox.addItem("Tunisia");
									    homeCountryBox.addItem("Turkey");
									    homeCountryBox.addItem("Turkmenistan");
									    homeCountryBox.addItem("Turks And Caicos Islands");
									    homeCountryBox.addItem("Tuvalu");
									    homeCountryBox.addItem("Uganda");
									    homeCountryBox.addItem("Ukraine");
									    homeCountryBox.addItem("United Arab Emirates");
									    homeCountryBox.addItem("United Kingdom");//223
									    homeCountryBox.addItem("United States Minor Outlying Islands");
									    homeCountryBox.addItem("United States");
									    homeCountryBox.addItem("Uruguay");
									    homeCountryBox.addItem("Uzbekistan");
									    homeCountryBox.addItem("Vanuatu");
									    homeCountryBox.addItem("Venezuela");
									    homeCountryBox.addItem("Viet Nam");
									    homeCountryBox.addItem("Virgin Islands, British");
									    homeCountryBox.addItem("Virgin Islands, U.S.");
									    homeCountryBox.addItem("Wallis And Futuna");
									    homeCountryBox.addItem("Western Sahara");
									    homeCountryBox.addItem("Yemen");
									    homeCountryBox.addItem("Yugoslavia");
									    homeCountryBox.addItem("Zambia");
									    homeCountryBox.addItem("Zimbabwe");
									    homeCountryBox.setSelectedIndex(223);
									    hpCountry.add(homeCountryBox);
									    vp.add(hpCountry);
									    
									    Button done = new Button();
									    done.setText("Done");
									    done.addClickHandler(new ClickHandler() {
			
											public void onClick(ClickEvent event) {
												Planner.getUser().setAge(THIS_YEAR - ageBox.getSelectedIndex());
												Planner.getUser().setFullName(fullnameBox.getText());
												if (genderBox.getSelectedIndex() == 0) {
													Planner.getUser().setMale(true);
												} else {
													Planner.getUser().setMale(false);
												}
												Planner.getUser().setHomeCountry(homeCountryBox.getItemText(homeCountryBox.getSelectedIndex()));
												Planner.getUser().save();
												
												Planner.updateContent(State.TRIPS);
											}
									    	
									    });
									    vp.add(done);
									}
								}
							});
							
							add(vp);
						}
					});
		} else {
			final Button sendButton = new Button("Log in");
			add(sendButton);

			// Create a handler for the sendButton and nameField
			class MyHandler implements ClickHandler, KeyUpHandler {
				/**
				 * Fired when the user clicks on the sendButton.
				 */
				public void onClick(ClickEvent event) {
					getOpenIdEndpoint();
				}

				/**
				 * Fired when the user types in the nameField.
				 */
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						getOpenIdEndpoint();
					}
				}

				/**
				 * Send the name from the nameField to the server and wait for a
				 * response.
				 */
				private void getOpenIdEndpoint() {
					sendButton.setEnabled(false);
					openidService.getOpenIdEndpoint(
							new AsyncCallback<String>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									caught.printStackTrace();
									System.out
											.println("Remote Procedure Call - Failure");
								}

								public void onSuccess(String result) {
									Window.Location.assign(result);
								}
							});
				}
			}
			// Add a handler to send the name to the server
			MyHandler handler = new MyHandler();
			sendButton.addClickHandler(handler);
		}
	}

}
