package com.library.librarymanagementsystem.dao;

import com.library.librarymanagementsystem.dto.MemberDTO;
import com.library.librarymanagementsystem.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // 1. Member කෙනෙක් add කරන method එක (දැන් MemberDTO එකක් ගන්නවා)
    public boolean addMember(MemberDTO member) {
        String sql = "INSERT INTO members (member_id, name, email, phone, address) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getMemberId());
            ps.setString(2, member.getName());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getAddress());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. හැම member කෙනෙක්ම ගන්න method එක (දැන් List<MemberDTO> එකක් return කරනවා)
    public List<MemberDTO> getAllMembers() {
        List<MemberDTO> members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try (Connection conn = DatabaseUtil.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MemberDTO m = new MemberDTO(
                        rs.getString("member_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
                members.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    // 3. Member details update කරන method එක
    public boolean updateMember(MemberDTO member) {
        String sql = "UPDATE members SET name = ?, email = ?, phone = ?, address = ? WHERE member_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.setString(4, member.getAddress());
            ps.setString(5, member.getMemberId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Member delete කරන method එක
    public boolean deleteMember(String memberId) {
        String sql = "DELETE FROM members WHERE member_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, memberId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}