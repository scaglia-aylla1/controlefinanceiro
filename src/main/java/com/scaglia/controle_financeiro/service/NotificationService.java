package com.scaglia.controle_financeiro.service;

import com.scaglia.controle_financeiro.dto.ExpenseDTO;
import com.scaglia.controle_financeiro.entity.ProfileEntity;
import com.scaglia.controle_financeiro.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final ExpenseService expenseService;

    @Value("${controle.financeiro.frontend.url}")
    private String frontendUrl;

    //Enviar renda diária, Lembrete de despesas
    @Scheduled(cron = "0 0 22 * * *", zone = "America/Sao_Paulo")
    public void sendDailyIncomeExpenseReminder(){
        log.info("Job started: sendDailyIncomeExpenseReminder()");
        List<ProfileEntity> profiles = profileRepository.findAll();
        for (ProfileEntity profile : profiles){
            String body = "Olá " + profile.getFullName() + ",<br><br>"
                    + "Este é um lembrete amigável para adicionar sua receita e despesas para hoje no Controle Financeiro.<br><br>"
                    +"<a href="+frontendUrl+" style='display:inline-block;padding;10px 20px;background-color:#4CAF50;color:#fff;text-decoration:none;border-radius:5px;font-weigth:bold;'>Vá para Controle Financeiro</a>"
                    +"<br><br>Atenciosamente,<br> Time Controle Financeiro";
            emailService.sendEmail(profile.getEmail(), "Lembrete diário: adicione suas receitas e despesas", body);
        }
        log.info("Job completed: sendDailyIncomeExpenseReminder()");
    }


    @Scheduled(cron = "0 * * * * *", zone = "America/Sao_Paulo")
    public void sendDailyExpenseSummary() {
        log.info("Job started: sendDailyExpenseSummary()");
        List<ProfileEntity> profiles = profileRepository.findAll();

        for (ProfileEntity profile : profiles) {
            List<ExpenseDTO> todayExpenses =
                    expenseService.getExpensesForUserOnDate(profile.getId(), LocalDate.now());

            if (!todayExpenses.isEmpty()) {
                StringBuilder table = new StringBuilder();

                table.append("<table style='border-collapse:collapse;width:100%;'>")
                        .append("<thead>")
                        .append("<tr style='background-color:#f2f2f2;'>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>#</th>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>Nome</th>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>Quantia</th>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>Categoria</th>")
                        .append("<th style='border:1px solid #ddd;padding:8px;'>Data</th>")
                        .append("</tr>")
                        .append("</thead>")
                        .append("<tbody>");

                int i = 1;
                for (ExpenseDTO expense : todayExpenses) {
                    table.append("<tr>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>").append(i++).append("</td>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>").append(expense.getName()).append("</td>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>").append(expense.getAmount()).append("</td>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>")
                            .append(expense.getCategoryId() != null ? expense.getCategoryName() : "N/A")
                            .append("</td>")
                            .append("<td style='border:1px solid #ddd;padding:8px;'>").append(expense.getDate()).append("</td>")
                            .append("</tr>");
                }

                table.append("</tbody>")
                        .append("</table>");

                String body = "Olá " + profile.getFullName() + ",<br><br>"
                        + "Aqui está um resumo de suas despesas para hoje:<br><br>"
                        + table
                        + "<br><br>Atenciosamente,<br>Time Controle Financeiro";

                emailService.sendEmail(profile.getEmail(), "Seu resumo de despesas diárias", body);
            }
        }

        log.info("Job completed: sendDailyExpenseSummary()");
    }

}
