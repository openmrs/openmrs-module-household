package org.openmrs.module.household.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;

public class PatientSearchServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5212530508391369208L;
	protected final Log log = LogFactory.getLog(getClass());

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        try {
            String phrase = request.getParameter("phrase");
            String[] tableHeading = {" ", "Select", "Name", "Age",
                "Gender", "Birthdate"/*, "Barcode"*/};
            if (phrase != null) {
                if (!(phrase.equals(""))) {
                    phrase = fixXSS(phrase);
                    List<Patient> patients = Context.getPatientService().getPatients(phrase);
                    int noOfPatients = patients.size();
                    out.println("<span id=\"matchCount\" >" + noOfPatients + " <a href=\"javascript:showPatients();\">(show/hide)</a></span>");
                    out.println("<span id=\"openmrsSearchDiv\" class=\"openmrsSearchDiv\">");
                    out.println("<table class=\"openmrsSearchTable\" style=\"width: 100%\" cellpadding=\"2\" cellspacing=\"0\">");

                    // <editor-fold defaultstate="collapsed" desc="Display Column Heading">
                    out.println("<thead><tr>");
                    for (int i = 0; i < tableHeading.length; i++) {
                        out.println("<th>" + tableHeading[i] + "</th>");
                    }
                    out.println("</tr></thead>");
                    // </editor-fold>

                    out.println("<tbody class=\"searchTBody\" style=\"vertical-align: middle\">");

                    // <editor-fold defaultstate="collapsed" desc="Display matching Patients">
                    if (noOfPatients < 1) {
                        out.println("<tr><td></td><td style=\"color:red\"><b>" + "No Patients Found" + "</b></td></tr>");
                    } else {
                        // <editor-fold defaultstate="collapsed" desc="Display max 10 patients page-wise">
                        if (noOfPatients > 10) {
                            String pageNum = request.getParameter("pageNum");
                            if (pageNum != null) {
                                if (!pageNum.equals("")) {
                                    int pageNo = Integer.parseInt(pageNum);
                                    for (int i = (pageNo - 1) * 10; i < ((pageNo - 1) * 10) + 10; i++) {
                                        if (i >= noOfPatients) {
                                            break;
                                        }
                                        if (i % 2 == 0) {
                                            out.println("<tr class=\"evenRow\">");
                                        } else {
                                            out.println("<tr class=\"oddRow\">");
                                        }
                                        //String patientIdentifier = patients.get(i).getPatientIdentifier().getIdentifier();
                                        out.println("<td id=\"sNo\" style=\"height:30px\">" + (i + 1) + ".) </td>");
                                        out.println("<td class=\"patientIdentifier\"><input type=\"checkbox\" id=\""+ patients.get(i).getPatientId() +"\" name=\""+ patients.get(i).getPatientId() +"\" onClick=\"selectedPerson("+ patients.get(i).getPatientId() +")\" /></td>");
                                        out.println("<td>" + patients.get(i).getGivenName() + " " + patients.get(i).getMiddleName() + " " + patients.get(i).getFamilyName() + "</td>");
                                        out.println("<td>" + String.valueOf(patients.get(i).getAge()) + "</td>");
                                        if (patients.get(i).getGender().equals("M")) {
                                            out.println("<td><img src=\"" + contextPath + "/images/male.gif\" /></td>");
                                        } else if (patients.get(i).getGender().equals("F")) {
                                            out.println("<td><img src=\"" + contextPath + "/images/female.gif\" /></td>");
                                        } else {
                                            out.println("<td>Unknown</td>");
                                        }
                                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                                        String birthDate = formatter.format(patients.get(i).getBirthdate());
                                        if (patients.get(i).isBirthdateEstimated()) {
                                            birthDate = '\u2248' + "  " + birthDate;
                                        } else {
                                            birthDate = "&nbsp;&nbsp;&nbsp;" + birthDate;
                                        }
                                        out.println("<td>" + birthDate + "</td>");
                                        /*out.println("<td class=\"printCmd\">");
                                        out.println("<a href=\"" + contextPath + "/module/registration/printDialog.htm?height=500&width=610&msg=" + patientIdentifier + "&TB_iframe=true\" class=\"thickbox\">Print Barcode</a>");
                                        out.println("</td></tr>");*/
                                        out.println("</tr>");
                                    }
                                    out.println("<tr id=\"pageRow\">&nbsp;</tr><tr id=\"pageRow\"><td></td>");
                                    if (pageNo != 1) {
                                        out.println("<td><a href=\"javascript:searchPatients(" + (pageNo - 1) + ",'" + phrase + "')\">Previous 10</a></td>");
                                    } else {
                                        out.print("<td></td>");
                                    }
                                    if ((pageNo - 1) < (noOfPatients / 10)) {
                                        out.println("<td><a href=\"javascript:searchPatients(" + (pageNo + 1) + ",'" + phrase + "')\">Next 10</a></td>");
                                    }
                                    out.print("</tr>");
                                }
                            } else {
                                out.println("<td style=\"font-size:20px\"> Don't try to be smart </td>");
                            }
                            //</editor-fold>
                        } // <editor-fold defaultstate="collapsed" desc="Display single page of patients">
                        else {
                            for (int i = 0; i < noOfPatients; i++) {
                                if (i % 2 == 0) {
                                    out.println("<tr class=\"evenRow\">");
                                } else {
                                    out.println("<tr class=\"oddRow\">");
                                }
                                //String patientIdentifier = patients.get(i).getPatientIdentifier().getIdentifier();
                                out.println("<td style=\"height:30px\">" + (i + 1) + ".) </td>");
                                out.println("<td class=\"patientIdentifier\"><input type=\"checkbox\" id=\""+ patients.get(i).getPatientId() +"\" name=\""+ patients.get(i).getPatientId() +"\" onClick=\"selectedPerson("+ patients.get(i).getPatientId() +")\" /></td>");
                                out.println("<td>" + patients.get(i).getGivenName() + " " + patients.get(i).getMiddleName() + " " + patients.get(i).getFamilyName() + "</td>");
                                out.println("<td>" + String.valueOf(patients.get(i).getAge()) + "</td>");
                                if (patients.get(i).getGender().equals("M")) {
                                    out.println("<td><img src=\"" + contextPath + "/images/male.gif\" /></td>");
                                } else if (patients.get(i).getGender().equals("F")) {
                                    out.println("<td><img src=\"" + contextPath + "/images/female.gif\" /></td>");
                                } else {
                                    out.println("<td>Unknown</td>");
                                }
                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                                String birthDate = formatter.format(patients.get(i).getBirthdate());
                                if (patients.get(i).isBirthdateEstimated()) {
                                    birthDate = '\u2248' + "  " + birthDate;
                                }
                                out.println("<td>" + birthDate + "</td>");
                                /*out.println("<td class=\"printCmd\">");
                                out.println("<a href=\"" + contextPath + "/module/registration/printDialog.htm?height=500&width=610&msg=" + patientIdentifier + "&TB_iframe=true\" class=\"thickbox\">Print Barcode</a>");
                                out.println("</td></tr>");*/
                                out.println("</tr>");
                            }
                            //</editor-fold>
                        }
                        // </editor-fold>                        
                        out.println("</tbody></table>");
                        out.println("</span>");
                    }
                }
            }
        } catch (APIAuthenticationException apiauthex) {
            out.println("Auth Error");
            log.error("API Auth Exception while searching patient: " + apiauthex.getMessage());
        } catch (Exception e) {
            log.error("General Exception while searching patient: " + e.getMessage());
        } finally {
            out.close();
        }
    }

    private String fixXSS(String str) {
        str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        str = str.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        str = str.replaceAll("'", "&#39;");
        str = str.replaceAll("eval\\((.*)\\)", "");
        str = str.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        str = str.replaceAll("script", "");
        return str;
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Calls processRequest when POST request is received">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //do Nothing... Can prevent XST
        //processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to Search for Patients";
    }// </editor-fold>
}