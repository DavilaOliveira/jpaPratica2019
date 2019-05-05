package aplicacao;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Pessoa;

public class Programa {
	public static void main(String[] args) {

//--------------------------------------------------------------------------------------------------------------------------
		Pessoa pessoa;
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Scanner acesso = new Scanner(System.in);
		int preferencia;

//--------------------------------------------------------------------------------------------------------------------------
		while (true) {

			System.out.println(
					"Digite 1: Para listar as pessoas cadastradas\n" + "Digite 2: Para buscar as pessoas pelo ID\n"
							+ "Digite 3: Para cadastrar uma pessoa\n" + "Digite 4: Para atualizar uma pessoa\n"
							+ "Digite 5: Para remover uma pessoa\n" + "Digite 0: Para sair");

//--------------------------------------------------------------------------------------------------------------------------

			preferencia = pedirInteiro(acesso, "Digite uma das opcões acima: ");

			switch (preferencia) {

			case 1:
				String jpql = "SELECT p FROM Pessoa p";
				List<Pessoa> pessoas = entityManager.createQuery(jpql, Pessoa.class).getResultList();

				for (int i = 0; i < pessoas.size(); i++) {
					System.out.println("Pessoa " + (i + 1) + ":");
					System.out.println(pessoas.get(i) + "\n");
				}
				System.out.println();
				break;
//--------------------------------------------------------------------------------------------------------------------------

			case 2:
				int id = pedirInteiro(acesso, "Digite o id: ");

				Pessoa pessoaFound = entityManager.find(Pessoa.class, id);
				if (pessoaFound == null) {
					System.out.println("Não existe a pessoa com o ID digitado.");
				} else {
					System.out.println(pessoaFound);
				}

				break;
//--------------------------------------------------------------------------------------------------------------------------

			case 3:
				System.out.println("Digite o nome: ");
				String nome = acesso.nextLine();
				System.out.println("Digite o email: ");
				String email = acesso.nextLine();
				pessoa = new Pessoa(null, nome, email);
				entityManager.getTransaction().begin();
				entityManager.persist(pessoa);
				entityManager.getTransaction().commit();
				break;

//--------------------------------------------------------------------------------------------------------------------------

			case 4:
				int id1 = pedirInteiro(acesso, "Digite o ID: ");
				Pessoa pessoaFound1 = entityManager.find(Pessoa.class, id1);

				if (pessoaFound1 != null) {
					System.out.println("Digite o nome atual: ");
					String nome1 = acesso.nextLine();
					System.out.println("Digite o email atual: ");
					String email1 = acesso.nextLine();
					pessoaFound1.setEmail(email1);
					pessoaFound1.setNome(nome1);
					entityManager.getTransaction().begin();
					entityManager.persist(pessoaFound1);
					entityManager.getTransaction().commit();

				} else {
					System.out.println("Não é possível a atualização pois o id digitado não existe no Banco de Dados.");
				}
				break;

//--------------------------------------------------------------------------------------------------------------------------

			case 5:
				int id2 = pedirInteiro(acesso, "Digite o ID que almeja remover: ");
				Pessoa pessoaFound2 = entityManager.find(Pessoa.class, id2);
				if (pessoaFound2 == null) {
					System.out.println(
							"Não é possível remover o id solicitado, haja vista sua inexistência no Banco de Dados.");
				} else {
					System.out.println(pessoaFound2);
				}
				entityManager.getTransaction().begin();
				entityManager.remove(pessoaFound2);
				entityManager.getTransaction().commit();
				break;

//--------------------------------------------------------------------------------------------------------------------------

			case 0:
				System.out.println("Fechando o programa...Tchau!");
				entityManager.close();
				entityManagerFactory.close();
				System.exit(0);
				break;

			default:
				System.out.println("Verifique se o número que solicitou coincide com as opções acessíveis.");
				break;
			}

//--------------------------------------------------------------------------------------------------------------------------

//			entityManger.getTransaction().commit();
//			System.out.println(pessoa);

		}

	}

	public static int pedirInteiro(Scanner acesso, String mensagem) {
		int valor;
		System.out.println(mensagem);

		while (true) {
			try {
				valor = Integer.parseInt(acesso.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("Tente outra vez!");
			}
		}

		return valor;
	}
}

//		entityManager.getTransaction().begin();
//		entityManager.persist(pessoa);
//		entityManager.getTransaction().commit();
//		entityManager.close();
//		entityManagerFactory.close();