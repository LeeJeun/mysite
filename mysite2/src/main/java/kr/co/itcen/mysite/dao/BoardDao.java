package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;


public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.1.165:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}
		
		return connection;
	}
	
	public Boolean insert(BoardVo vo) {
		Boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = "insert into board values(null, ?, ?, 0, now(), (select ifnull(max(g_no)+1, 1) from board a), 1, 0, ?, 0)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select last_insert_id()");
			if(rs.next()) {
				Long no = rs.getLong(1);
				vo.setNo(no);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
	
	public Boolean replyInsert(BoardVo vo) {
		Boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		int oNo = vo.getoNo()+1;
		int depth = vo.getDepth()+1;
		
		try {
			connection = getConnection();
			
			//1. update
			//원글(oNo==1)이 아니면
			if(oNo != 1) {
				stmt = connection.createStatement();
				stmt.executeUpdate("update board set o_no=o_no+1 where g_no = " + vo.getgNo() + " and o_no >= " + oNo);
			}
			
			//2. insert
			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?, 0)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo());
			pstmt.setInt(4, oNo);
			pstmt.setInt(5, depth);
			pstmt.setLong(6, vo.getUserNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
	
	public Boolean update(BoardVo vo) {
		Boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		Statement stmt = null;
		
		try {
			connection = getConnection();
			
			String sql = "update board set title = ?, contents = ?, status_no = 1 where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
			stmt = connection.createStatement();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;		
	}
	
	public Boolean replyDelete(BoardVo vo) {
		Boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = getConnection();
			
			String sql = "update board set title = ?, status_no = 2 where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "삭제된 게시물 입니다.");
			pstmt.setLong(2, vo.getNo());
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;		
	}
	
	public List<BoardVo> getList(int curPage) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = 
				"select b.no, b.title, b.user_no, u.name, b.hit, b.g_no, b.depth, b.status_no, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s')\r\n" + 
				"from board b, user u\r\n" + 
				"where b.user_no = u.no\r\n" + 
				"order by g_no desc, o_no asc\r\n" +
				"limit ?, 5";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, (curPage-1)*5);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				Long userNo = rs.getLong(3);
				String userName = rs.getString(4);
				int hit = rs.getInt(5);
				int gNo = rs.getInt(6);
				int depth = rs.getInt(7);
				Long statusNo = rs.getLong(8);
				String regDate = rs.getString(9);
				
				BoardVo vo= new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setHit(hit);
				vo.setgNo(gNo);
				vo.setDepth(depth);
				vo.setStatusNo(statusNo);
				vo.setRegDate(regDate);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<BoardVo> getList(String kwd, int curPage) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = 
				"select b.no, b.title, b.user_no, u.name, b.hit, b.g_no, b.depth, b.status_no, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s')\r\n" + 
				" from board b, user u\r\n" + 
				" where b.user_no = u.no\r\n" + 
				" and (b.title like ? or b.contents like ?)\r\n" +
				" limit ?, 5";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");
			pstmt.setInt(3, (curPage-1)*5);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				Long userNo = rs.getLong(3);
				String userName = rs.getString(4);
				int hit = rs.getInt(5);
				int gNo = rs.getInt(6);
				int depth = rs.getInt(7);
				Long statusNo = rs.getLong(8);
				String regDate = rs.getString(9);
				
				BoardVo vo= new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setHit(hit);
				vo.setgNo(gNo);
				vo.setDepth(depth);
				vo.setStatusNo(statusNo);
				vo.setRegDate(regDate);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int getCount() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			connection = getConnection();
			
			String sql = "select count(*) from board";
				
			pstmt = connection.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	// 검색용
	public int getCount(String kwd) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			connection = getConnection();
			
			String sql = 
					"select count(*)\r\n" + 
					"from board\r\n" + 
					"where title like ?\r\n" +
					"or contents like ?";
				
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	//답글유무 확인용
	public int getCount(int gNo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			connection = getConnection();
			
			String sql = 
					"select count(*)\r\n" + 
					"from board\r\n" + 
					"where g_no like ?";
				
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, gNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public List<BoardVo> getView(Long boardNo) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Statement stmt = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			//조회수 증가
			stmt = connection.createStatement();
			stmt.executeUpdate("update board set hit=hit+1 where no = " + boardNo);

			String sql = 
				"select no, title, contents, g_no, o_no, depth, user_No\r\n" + 
				"from board\r\n" + 
				"where no=?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int gNo = rs.getInt(4);
				int oNo = rs.getInt(5);
				int depth = rs.getInt(6);
				Long userNo = rs.getLong(7);
				
				BoardVo vo= new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				
				result.add(vo);
			}
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void delete(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = getConnection();
			
			String sql =
				" delete" +
				"   from board" +
				"  where no = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}	
}
