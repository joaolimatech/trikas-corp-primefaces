package br.com.trikascrm.view;

import br.com.trikascrm.database.Repository;
import br.com.trikascrm.model.UsuarioVO;
import br.com.trikascrm.service.Service;
import com.sun.faces.util.CollectionsUtils;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped

public class FuncionarioMBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(FuncionarioMBean.class);
    public FuncionarioMBean(){
        service = new Service();
        limparCampos();
    }

    public void limparCampos(){
        matriculaPesquisa = null;
        lstFuncionarios = new ArrayList<>();
        usuarioGenericoVO = new UsuarioVO();
    }

    private Service service;


    @Inject
    private UsuarioSessaoMBean usuarioSessaoMBean;
    private List<UsuarioVO> lstFuncionarios;
    private String matriculaPesquisa;
    private boolean renderConsultaFuncionarios;
    private UsuarioVO usuarioGenericoVO;
    private Date date1;
    private LocalDate localDate;
    private String txt1;
    String matriculaCaraLogado;
    //O ideal é fazer essa lista buscar de alguma tabela
    private List<String> cargos = Arrays.asList(  "Vendedor", "Gerente", "Analista Sustentação", "Desenvolvedor Back-end", "Desenvolvedor Front-end", "Database Analyst", "Suporte", "Coordenador",  "Analista de Negócios", "Consultor TI", "Executivo de Contas", "Customer Success", "Analista de Dados", "Gerente Comercial", "Product Owner",  "Analista Financeiro"
    );

    @PostConstruct
    public void init(){
          matriculaCaraLogado = usuarioSessaoMBean.getUsuarioLogado().getMatricula().trim();
    }

    public void pesquisarFuncionarioMatricula(){
        if(matriculaPesquisa!=null && !matriculaPesquisa.trim().isEmpty()){
            matriculaPesquisa = matriculaPesquisa.trim();
            matriculaPesquisa =  matriculaPesquisa.equalsIgnoreCase("all")? null : matriculaPesquisa;

            lstFuncionarios = service.pesquisarFuncionario(matriculaPesquisa);
            if(CollectionUtils.isEmpty(lstFuncionarios)){
               showMessageErro("Nenhum funcionario encontrado para a matricula: " + matriculaPesquisa);
               return;
            } else {
                renderConsultaFuncionarios = !CollectionUtils.isEmpty(lstFuncionarios);
            }

        }

    }

    public void cadastrarFuncionario(){
        log.info("{} clicou em cadastrarFuncionario() para a matricula: {}", usuarioSessaoMBean.getUsuarioLogado().getMatricula(), usuarioGenericoVO.getMatricula());

        if(service.validarUserNaBase(usuarioGenericoVO.getMatricula().trim())){
showMessageErro("Matricula ja existe na base.");
        return;}



        boolean cargoValido = cargos.stream().anyMatch(c -> c.equalsIgnoreCase(txt1.toLowerCase()));
        if(!cargoValido){
            showMessageErro("Cargo invalido. Por favor, escolha um cargo da lista de sugestoes.");
            return;
        }
        usuarioGenericoVO.setNome(capitalizar(usuarioGenericoVO.getNome()));
        String email = createEmail(usuarioGenericoVO.getNome());
        if(email==null){
            showMessageErro("Nome deve conter pelo menos nome e sobrenome");
            return;
        }

        LocalDateTime dataAgora = LocalDateTime.now();
        LocalDateTime dataNascimentoLocalDate = date1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        int idade = Period.between(dataNascimentoLocalDate.toLocalDate(), dataAgora.toLocalDate()).getYears();
        if(idade<18){
            showMessageErro("Não é permitido funcionários menores de 18 anos.");
            return;
        }
        usuarioGenericoVO.setDataNascimento(formatarData(date1));
        log.info("{} - Tudo certo para cadastrar o funcionario {} ", matriculaCaraLogado,usuarioGenericoVO.toString());



    }

    public List<String> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
//        List<String> countryList = new ArrayList<>();
//        List<Country> countries = countryService.getCountries();
//        for (Country country : countries) {
//            countryList.add(country.getName());
//        }

        return cargos.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public String createEmail(String nome){
        nome = nome.toLowerCase();
        List<String> partesNome = Arrays.asList(nome.split(" "));
        if(partesNome.size()<2){
            return null;
        }
        String email = partesNome.get(0) + "."+partesNome.get(partesNome.size()-1)+"@trikas.com";
        return  email;
    }


    public void showMessageErro(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "ERRO",
                        msg
                )
        );
        log.info("{} erro -> {}",matriculaCaraLogado, msg);

    }

    public void showMessageSucess(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "SUCESSO",
                        msg
                )
        );
        log.info("{} Sucesso -> {}",matriculaCaraLogado, msg);

    }

    public String formatarData(Date date) {
       localDate = date.toInstant()
               .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDate.format(formatador);
    }

    public String capitalizar(String nameINput){
        nameINput = nameINput.trim();
        List<String> nomeLst = Arrays.asList(nameINput.split(" "));
        String nomeFinal = "";
        for (String nome : nomeLst) {
            nomeFinal+= nome.substring(0,1).toUpperCase() + nome.substring(1).toLowerCase()+ " ";
        }
        return nomeFinal.trim();
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public UsuarioSessaoMBean getUsuarioSessaoMBean() {
        return usuarioSessaoMBean;
    }

    public void setUsuarioSessaoMBean(UsuarioSessaoMBean usuarioSessaoMBean) {
        this.usuarioSessaoMBean = usuarioSessaoMBean;
    }

    public List<UsuarioVO> getLstFuncionarios() {
        return lstFuncionarios;
    }

    public void setLstFuncionarios(List<UsuarioVO> lstFuncionarios) {
        this.lstFuncionarios = lstFuncionarios;
    }

    public String getMatriculaPesquisa() {
        return matriculaPesquisa;
    }

    public void setMatriculaPesquisa(String matriculaPesquisa) {
        this.matriculaPesquisa = matriculaPesquisa;
    }

    public boolean isRenderConsultaFuncionarios() {
        return renderConsultaFuncionarios;
    }

    public void setRenderConsultaFuncionarios(boolean renderConsultaFuncionarios) {
        this.renderConsultaFuncionarios = renderConsultaFuncionarios;
    }

    public UsuarioVO getUsuarioGenericoVO() {
        return usuarioGenericoVO;
    }

    public void setUsuarioGenericoVO(UsuarioVO usuarioGenericoVO) {
        this.usuarioGenericoVO = usuarioGenericoVO;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public List<String> getCargos() {
        return cargos;
    }

    public void setCargos(List<String> cargos) {
        this.cargos = cargos;
    }
}
