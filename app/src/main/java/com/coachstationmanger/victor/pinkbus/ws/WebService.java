package com.coachstationmanger.victor.pinkbus.ws;

import com.coachstationmanger.victor.pinkbus.models.BookingPerson;
import com.coachstationmanger.victor.pinkbus.models.City;
import com.coachstationmanger.victor.pinkbus.models.Company;
import com.coachstationmanger.victor.pinkbus.models.RouteDetails;
import com.coachstationmanger.victor.pinkbus.models.Seat;
import com.coachstationmanger.victor.pinkbus.models.Ticket;
import com.coachstationmanger.victor.pinkbus.models.TicketDetails;
import com.coachstationmanger.victor.pinkbus.models.TicketOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Victor on 08/04/2017.
 */

public class WebService {
    private String NAME_SPACES="http://coachstationmanager.com/";
    private String URL="http://192.168.1.217/CoachStationWS/MyWebService.asmx";

    public List<Company> GetCompanies()
    {
        List<Company> list=new ArrayList<>();
        String METHOD_NAME="GetListCompany";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;
        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response= (SoapPrimitive) envelope.getResponse();
            String responseJson=response.toString();
            port.debug=true;
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for ( int i=0; i<jsonArray.length();i++)
                {
                    Company c = new Company();
                    c.setCompanyId(jsonArray.getJSONObject(i).getString("COMPANY_ID"));
                    c.setCompanyName(jsonArray.getJSONObject(i).getString("COMPANY_NAME"));
                    c.setCompanyTel(jsonArray.getJSONObject(i).getString("COMPANY_TEL"));
                    list.add(c);
                    port.debug=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Integer DeleteTicket(TicketOrder tk)
    {
        int result=0;
        String METHOD_NAME="android_DELETE_TICKET";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("ticket_id");
        propertyInfo.setValue(tk.getTicketID());
        propertyInfo.setType(Integer.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("email");
        propertyInfo1.setValue(tk.getEmail());
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);

        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response= (SoapPrimitive) envelope.getResponse();
            return Integer.valueOf(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }

    public BookingPerson LoginPassenger(String email,String password)
    {
        BookingPerson p=new BookingPerson();

        String METHOD_NAME="android_LOGIN_PASSENGER";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        //
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("email");
        propertyInfo.setValue(email);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("password");
        propertyInfo1.setValue(password);
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response= (SoapPrimitive) envelope.getResponse();
            String responseJson=response.toString();
            if(responseJson.length()==1)
            {
                p.setStrEmail(responseJson);
                return p;
            }
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0;i<jsonArray.length();i++)
                {
                    p.setStrEmail(jsonArray.getJSONObject(i).getString("EMAIL"));
                    p.setStrTel(jsonArray.getJSONObject(i).getString("BOOKING_PERSON_TEL"));
                    p.setStrName(jsonArray.getJSONObject(i).getString("BOOKING_PERSON_NAME"));
                    p.setLoginStatus(true);
                    port.debug=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return p;
    }

    public BookingPerson SignUpPassenger(BookingPerson person)
    {
        BookingPerson p=new BookingPerson();
        String METHOD_NAME="android_SIGNUP_PASSENGER";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        //
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("email");
        propertyInfo.setValue(person.getStrEmail());
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("tel");
        propertyInfo1.setValue(person.getStrTel());
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        PropertyInfo propertyInfo2=new PropertyInfo();
        propertyInfo2.setName("name");
        propertyInfo2.setValue(person.getStrName());
        propertyInfo2.setType(String.class);
        request.addProperty(propertyInfo2);
        //
        PropertyInfo propertyInfo3=new PropertyInfo();
        propertyInfo3.setName("password");
        propertyInfo3.setValue(person.getStrPassword());
        propertyInfo3.setType(String.class);
        request.addProperty(propertyInfo3);
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response= (SoapPrimitive) envelope.getResponse();
            String responseJson=response.toString();
            if(responseJson.length()==1)
            {
                p.setStrEmail(responseJson);
                return p;
            }
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0;i<jsonArray.length();i++)
                {
                    p.setStrEmail(jsonArray.getJSONObject(i).getString("EMAIL"));
                    p.setStrTel(jsonArray.getJSONObject(i).getString("BOOKING_PERSON_TEL"));
                    p.setStrName(jsonArray.getJSONObject(i).getString("BOOKING_PERSON_NAME"));
                    p.setLoginStatus(true);
                    port.debug=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<TicketOrder> UpdatePayment(TicketOrder tk)
    {
        List<TicketOrder>list=new ArrayList<>();
        String METHOD_NAME="android_UPDATE_PAYMENT";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        //
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("order_id");
        propertyInfo.setValue(tk.getOrder_Id());
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("payment_id");
        propertyInfo1.setValue(tk.getPayment_Id());
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        PropertyInfo propertyInfo2=new PropertyInfo();
        propertyInfo2.setName("ticket_id");
        propertyInfo2.setValue(tk.getTicketID());
        propertyInfo2.setType(Integer.class);
        request.addProperty(propertyInfo2);

        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet=true;

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response=(SoapPrimitive) envelope.getResponse();
            String responseJson=response.toString();
            try {
                if (responseJson.compareTo("0")==0)
                {
                    TicketOrder tko=new TicketOrder();
                    tko.setEmail("0");
                    list.add(tko);
                    return list;
                }
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                    TicketOrder order=new TicketOrder();
                    List<Integer> listTicketID=new ArrayList<>();
                    order.setTicketID(jsonArray.getJSONObject(i).getInt("TICKET_ID"));
                    order.setEmail(jsonArray.getJSONObject(i).getString("EMAIL"));
                    order.setOrder_Date(jsonArray.getJSONObject(i).getString("ORDER_DATE"));
                    order.setPayout_Date(jsonArray.getJSONObject(i).getString("PAYOUT_DATE"));
                    order.setReceived_Date(jsonArray.getJSONObject(i).getString("RECEIVED_DATE"));
                    order.setStatus_Ticket(jsonArray.getJSONObject(i).getString("STATUS_TICKET"));
                    listTicketID.add(jsonArray.getJSONObject(i).getInt("TICKET_ID"));
                    order.setListTicketId(listTicketID);
                    list.add(order);
                    port.debug=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TicketOrder> TicketOrderList(String order_id,
                                             String email)
    {
        List<TicketOrder> list=new ArrayList<>();
        String METHOD_NAME="android_TICKET_ORDER";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        //
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("order_id");
        propertyInfo.setValue(order_id);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("email");
        propertyInfo1.setValue(email);
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);

        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response= (SoapPrimitive) envelope.getResponse();
            String responseJson=response.toString();
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                    TicketOrder to=new TicketOrder();
                    to.setEmail(jsonArray.getJSONObject(i).getString("EMAIL"));
                    to.setTicketID(jsonArray.getJSONObject(i).getInt("TICKET_ID"));
                    to.setOrder_Date(jsonArray.getJSONObject(i).getString("ORDER_DATE"));
                    list.add(to);
                    port.debug=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Integer BookingTicket (TicketDetails ticket)
    {
        int result=0;
        String METHOD_NAME="android_BOOKING_TICKET";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        //
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("tel");
        propertyInfo.setValue(ticket.getTelOfPassenger());
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("name");
        propertyInfo1.setValue(ticket.getNameOfPassenger());
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        PropertyInfo propertyInfo2=new PropertyInfo();
        propertyInfo2.setName("dpt_date");
        propertyInfo2.setValue(ticket.getDepartureDate());
        propertyInfo2.setType(String.class);
        request.addProperty(propertyInfo2);
        //
        PropertyInfo propertyInfo3=new PropertyInfo();
        propertyInfo3.setName("boarding");
        propertyInfo3.setValue(ticket.getBoardingLocal());
        propertyInfo3.setType(String.class);
        request.addProperty(propertyInfo3);
        //
        PropertyInfo propertyInfo4=new PropertyInfo();
        propertyInfo4.setName("dropping");
        propertyInfo4.setValue(ticket.getDroppingLocal());
        propertyInfo4.setType(String.class);
        request.addProperty(propertyInfo4);
        //
        PropertyInfo propertyInfo5=new PropertyInfo();
        propertyInfo5.setName("seat_id");
        propertyInfo5.setValue(ticket.getSeatID());
        propertyInfo5.setType(String.class);
        request.addProperty(propertyInfo5);
        //
        PropertyInfo propertyInfo6=new PropertyInfo();
        propertyInfo6.setName("coach_route_id");
        propertyInfo6.setValue(ticket.getCoachRouteID());
        propertyInfo6.setType(String.class);
        request.addProperty(propertyInfo6);
        //
        PropertyInfo propertyInfo7=new PropertyInfo();
        propertyInfo7.setName("email");
        propertyInfo7.setValue(ticket.getEmail());
        propertyInfo7.setType(String.class);
        request.addProperty(propertyInfo7);
        //
        PropertyInfo propertyInfo8=new PropertyInfo();
        propertyInfo8.setName("order_id");
        propertyInfo8.setValue(ticket.getOrderID());
        propertyInfo8.setType(String.class);
        request.addProperty(propertyInfo8);

        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet=true;

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response=(SoapPrimitive) envelope.getResponse();
            String responseJson=response.toString();
            port.debug=true;
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                    result=jsonArray.getJSONObject(i).getInt("RESULT");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Seat> GetListSeat(String from,String to, String dpt_date,String route_id, String company_id)
    {
        List<Seat> list=new ArrayList<>();

        String METHOD_NAME="android_SEAT_LIST";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("from");
        propertyInfo1.setValue(from);
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("to");
        propertyInfo.setValue(to);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo2=new PropertyInfo();
        propertyInfo2.setName("dp_date");
        propertyInfo2.setValue(dpt_date);
        propertyInfo2.setType(String.class);
        request.addProperty(propertyInfo2);
        //
        PropertyInfo propertyInfo3=new PropertyInfo();
        propertyInfo3.setName("route_id");
        propertyInfo3.setValue(route_id);
        propertyInfo3.setType(String.class);
        request.addProperty(propertyInfo3);
        //
        PropertyInfo propertyInfo4=new PropertyInfo();
        propertyInfo4.setName("company_id");
        propertyInfo4.setValue(company_id);
        propertyInfo4.setType(String.class);
        request.addProperty(propertyInfo4);
        //
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        //
        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);
        //
        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
            String responseJson=response.toString();
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                    Seat seat=new Seat();
                    seat.setSeatID(jsonArray.getJSONObject(i).getString("SEAT_ID"));
                    seat.setStatusSeat(jsonArray.getJSONObject(i).getInt("SEAT_STATUS"));
                    seat.setStLicense_Plate(jsonArray.getJSONObject(i).getString("LICENSE_PLATE"));
                    list.add(seat);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> GetListDropping(String coach_route_id,String company_name)
    {
        List<String> list=new ArrayList<>();
        String METHOD_NAME="android_DROPPING_LIST";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("couch_route_id");
        propertyInfo.setValue(coach_route_id);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("company_name");
        propertyInfo1.setValue(company_name);
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet=true;
        //
        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);
        //
        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
            String responseJson=response.toString();
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                    String boarding= jsonArray.getJSONObject(i).getString("Column1");
                    list.add(boarding);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> GetListBoarding(String coach_route_id,String company_name)
    {
        List<String> list=new ArrayList<>();
        String METHOD_NAME="android_BOARDING_LIST";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("couch_route_id");
        propertyInfo.setValue(coach_route_id);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("company_name");
        propertyInfo1.setValue(company_name);
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet=true;
        //
        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);
        //
        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
            String responseJson=response.toString();
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                   String boarding= jsonArray.getJSONObject(i).getString("Column1");
                    list.add(boarding);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<RouteDetails> GetListRouteDetails(String from_city,String to_city,String dpt_date)
    {
        List<RouteDetails> list=new ArrayList<>();
        String METHOD_NAME="android_ROUTE_DETAIL_LIST";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        PropertyInfo propertyInfo1=new PropertyInfo();
        propertyInfo1.setName("from");
        propertyInfo1.setValue(from_city);
        propertyInfo1.setType(String.class);
        request.addProperty(propertyInfo1);
        //
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("to");
        propertyInfo.setValue(to_city);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);
        //
        PropertyInfo propertyInfo2=new PropertyInfo();
        propertyInfo2.setName("dp_date");
        propertyInfo2.setValue(dpt_date);
        propertyInfo2.setType(String.class);
        request.addProperty(propertyInfo2);
        //
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        //
        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);
        //
        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            port.debug=true;
            SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
            String responseJson=response.toString();
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                    RouteDetails route=new RouteDetails();
                    route.setRoute_Price(jsonArray.getJSONObject(i).getInt("COACH_ROUTE_PRICE"));
                    route.setDeparture_Time(jsonArray.getJSONObject(i).getString("START_TIME"));
                    route.setRoute_Time(jsonArray.getJSONObject(i).getInt("ROUTE_TIME"));
                    route.setTotal_Seat(jsonArray.getJSONObject(i).getInt("TOTAL_SEATS"));
                    route.setType_Seat(jsonArray.getJSONObject(i).getString("TYPE_SEAT"));
                    route.setCompany_Name(jsonArray.getJSONObject(i).getString("COMPANY_NAME"));
                    route.setArrival_Time(jsonArray.getJSONObject(i).getString("END_TIME"));
                    String coach_route_id=jsonArray.getJSONObject(i).getString("COACH_ROUTE_ID");
                    route.setCoach_Route_Id(coach_route_id);
                    route.setListBoarding(GetListBoarding(coach_route_id,
                            jsonArray.getJSONObject(i).getString("COMPANY_NAME")));
                    route.setListDropping(GetListDropping(coach_route_id,
                            jsonArray.getJSONObject(i).getString("COMPANY_NAME")));
                    String company_id =jsonArray.getJSONObject(i).getString("COMPANY_ID");
                    route.setSeatList(GetListSeat(from_city,to_city,dpt_date,coach_route_id,company_id));
                    list.add(route);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> GetListFromCiTy()
    {
        List<String>list=new ArrayList<>();
        String METHOD_NAME="android_FROM_CITY_LIST";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;

        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            SoapPrimitive response=(SoapPrimitive) envelope.getResponse();
            //Vi envelope tra ve la mot json
            String responseJSON=response.toString();
            try {
                JSONArray jsonArray=new JSONArray(responseJSON);
                for (int i=0; i<jsonArray.length(); i++)
                {
                    String city=jsonArray.getJSONObject(i).getString("CITY");
                    list.add(city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }
    //Lay to city tu ws
    public List<String> GetListToCity(String from_city) {
        String METHOD_NAME="android_TO_CITY_LIST";
        String SOAP_ACTION=NAME_SPACES+METHOD_NAME;
        List<String> list=new ArrayList<>();

        SoapObject request=new SoapObject(NAME_SPACES,METHOD_NAME);
        PropertyInfo propertyInfo=new PropertyInfo();
        propertyInfo.setName("from_city");
        propertyInfo.setValue(from_city);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);

        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        MarshalFloat marshalFloat=new MarshalFloat();
        marshalFloat.register(envelope);

        HttpTransportSE port=new HttpTransportSE(URL);
        try {
            port.call(SOAP_ACTION,envelope);
            port.debug=true;
            SoapPrimitive response=(SoapPrimitive) envelope.getResponse();
            String responseJson=response.toString();
            try {
                JSONArray jsonArray=new JSONArray(responseJson);
                for (int i=0; i<jsonArray.length();i++)
                {
                    String city=jsonArray.getJSONObject(i).getString("DROPPING");
                    list.add(city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }
}
