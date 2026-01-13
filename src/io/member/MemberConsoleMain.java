package io.member;

import io.member.impl.DataMemberRepository;
import io.member.impl.FileMemberRepository;
import io.member.impl.MemoryMemberRepository;
import io.member.impl.ObjectMemberRepository;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MemberConsoleMain {

    //private static final MemberRepository repository = new MemoryMemberRepository();
    //private static final MemberRepository repository = new DataMemberRepository();
    private static final MemberRepository repository = new ObjectMemberRepository();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1.회원 등록 | 2. 회원 목록 조회 | 3. 종료");
            System.out.println("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); //newline 제거

            switch (choice) {
                case 1:
                    //회원 등록
                    registerMember (scanner);
                    break;
                case 2:
                    //회원 목록 조회
                    displayMembers ();
                    break;
                case 3:
                    System.out.println("프로그램 종료");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void registerMember(Scanner scanner) {
        System.out.println("ID 입력: ");
        String id = scanner.nextLine();

        System.out.println("Name 입력 ");
        String name = scanner.nextLine();

        System.out.println("Age input");
        int age = scanner.nextInt();

        Member newMember = new Member(id, name, age);
        try {
            repository.add(newMember);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayMembers() {
        List<Member> members = repository.findAll();
        for (Member member : members) {
            System.out.println("member id= " + member.getId());
            System.out.println("member name= " + member.getName());
            System.out.println("member age= " + member.getAge());
        }
    }
}
