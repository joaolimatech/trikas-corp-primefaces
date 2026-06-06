package br.com.trikascrm.view;

import br.com.trikascrm.model.UsuarioVO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class HomeMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioSessaoMBean usuarioSessaoMBean;

    private List<Kpi> kpis;
    private List<Atividade> atividades;
    private List<AcessoRapido> acessosRapidos;

    @PostConstruct
    public void init() {
        kpis = List.of(
                new Kpi("Leads hoje", "128", "+18% vs. ontem", "pi pi-users", "sales"),
                new Kpi("Negócios ativos", "42", "8 em negociação", "pi pi-briefcase", "pipeline"),
                new Kpi("Taxa de resposta", "94%", "Últimas 24h", "pi pi-bolt", "service"),
                new Kpi("Receita prevista", "R$ 218k", "Próximos 30 dias", "pi pi-chart-line", "finance")
        );

        atividades = List.of(
                new Atividade("Ana Souza", "Agendou demonstração", "Lead quente", "pi pi-calendar-plus", "10 min"),
                new Atividade("Carlos Lima", "Atualizou etapa no pipeline", "Proposta enviada", "pi pi-sync", "35 min"),
                new Atividade("Marina Costa", "Fechou contrato", "Cliente ativo", "pi pi-check-circle", "1 h"),
                new Atividade("Equipe comercial", "Nova fila de tarefas criada", "Backoffice", "pi pi-list", "Hoje")
        );

        acessosRapidos = List.of(
                new AcessoRapido("Novo lead", "pi pi-user-plus", "lead"),
                new AcessoRapido("Criar tarefa", "pi pi-check-square", "task"),
                new AcessoRapido("Abrir funil", "pi pi-sitemap", "pipeline"),
                new AcessoRapido("Ver relatórios", "pi pi-chart-bar", "report")
        );
    }

    public UsuarioVO getUsuarioLogado() {
        return usuarioSessaoMBean != null ? usuarioSessaoMBean.getUsuarioLogado() : null;
    }

    public List<Kpi> getKpis() {
        return kpis;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public List<AcessoRapido> getAcessosRapidos() {
        return acessosRapidos;
    }

    public String getNomeExibicao() {
        UsuarioVO usuario = getUsuarioLogado();
        return usuario == null ? "Visitante" : usuario.getNome();
    }

    public String getPerfilExibicao() {
        UsuarioVO usuario = getUsuarioLogado();
        return usuario == null ? "Sem acesso" : usuario.getPerfil();
    }

    public String getStatusExibicao() {
        return getUsuarioLogado() == null ? "Não autenticado" : "Sessão ativa";
    }

    public String getPrimeiraLetra() {
        String nome = getNomeExibicao();
        return nome.isBlank() ? "T" : nome.substring(0, 1).toUpperCase();
    }

    public static class Kpi implements Serializable {
        private final String titulo;
        private final String valor;
        private final String detalhe;
        private final String icone;
        private final String classe;

        public Kpi(String titulo, String valor, String detalhe, String icone, String classe) {
            this.titulo = titulo;
            this.valor = valor;
            this.detalhe = detalhe;
            this.icone = icone;
            this.classe = classe;
        }

        public String getTitulo() { return titulo; }
        public String getValor() { return valor; }
        public String getDetalhe() { return detalhe; }
        public String getIcone() { return icone; }
        public String getClasse() { return classe; }
    }

    public static class Atividade implements Serializable {
        private final String nome;
        private final String descricao;
        private final String contexto;
        private final String icone;
        private final String tempo;

        public Atividade(String nome, String descricao, String contexto, String icone, String tempo) {
            this.nome = nome;
            this.descricao = descricao;
            this.contexto = contexto;
            this.icone = icone;
            this.tempo = tempo;
        }

        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public String getContexto() { return contexto; }
        public String getIcone() { return icone; }
        public String getTempo() { return tempo; }
    }

    public static class AcessoRapido implements Serializable {
        private final String titulo;
        private final String icone;
        private final String chave;

        public AcessoRapido(String titulo, String icone, String chave) {
            this.titulo = titulo;
            this.icone = icone;
            this.chave = chave;
        }

        public String getTitulo() { return titulo; }
        public String getIcone() { return icone; }
        public String getChave() { return chave; }
    }
}
