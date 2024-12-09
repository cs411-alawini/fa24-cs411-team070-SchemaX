package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.dto.CreateListingItemDTO;
import com.schemax.foodforward.dto.UpdateListingDTO;
import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Item;
import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.model.ListingItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ListingRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	public List<Listing> findAllByLatitudeIsNullAndLongitudeIsNull() {
		String sql = "SELECT * FROM Listing WHERE latitude IS NULL AND longitude IS NULL";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Listing listing = new Listing();
			listing.setListingId(rs.getLong("listing_id"));
			listing.setLocation(rs.getString("location"));
			return listing;
		});
	}

//	@SuppressWarnings("deprecation")
//	public List<Listing> findListingsWithFilters(String foodType, Long quantityNeeded, Date expiryDate,
//			String pickupTimeStart, String pickupTimeEnd, String location, Double distance, Double userLongitude,
//			Double userLatitude, String recipientId) {
//
//		String sql = """
//	            SELECT
//				    l.listing_id AS listingId,
//				    l.location AS location,
//				    l.pickup_time_range AS pickupTimeRange,
//				    l.status AS listingStatus,
//				    li.listing_item_id AS listingItemId,
//				    li.quantity AS quantity,
//				    li.expiration_date AS itemExpirationDate,
//				    li.status AS itemStatus,
//				    i.item_id AS itemId,
//				    i.item_name AS itemName,
//				    i.category AS category,
//				    d.donor_id AS donorId,
//				    u.name AS donorName,
//				    u.email AS donorEmail,
//				    u.phone AS donorPhone,
//				    1 AS priority -- Recommendations have higher priority
//				FROM Listing l
//				JOIN ListingItem li ON l.listing_id = li.listing_id
//				JOIN Item i ON li.item_id = i.item_id
//				JOIN Donor d ON l.listed_by = d.donor_id
//				JOIN User u ON d.user_id = u.user_id
//				JOIN Recommendation r ON r.donor_id = d.donor_id AND r.recipient_id = ?
//				WHERE
//				    (? IS NULL OR i.category = ?)
//				    AND (? IS NULL OR li.quantity >= ?)
//				    AND (? IS NULL OR li.expiration_date <= ?)
//				    AND (? IS NULL OR l.pickup_time_range >= ?)
//				    AND (? IS NULL OR l.pickup_time_range <= ?)
//				    AND (? IS NULL OR l.location LIKE CONCAT('%', ?, '%'))
//				    AND (? IS NULL OR (
//				        ? IS NOT NULL AND
//				        ? IS NOT NULL AND
//				        ST_Distance_Sphere(POINT(l.longitude, l.latitude), POINT(?, ?)) <= ?))
//				    AND l.status = 'ACTIVE'
//				    AND li.status = 'AVAILABLE'
//				    AND (li.expiration_date IS NULL OR li.expiration_date > CURRENT_DATE)
//
//				UNION ALL
//
//				SELECT
//				    l.listing_id AS listingId,
//				    l.location AS location,
//				    l.pickup_time_range AS pickupTimeRange,
//				    l.status AS listingStatus,
//				    li.listing_item_id AS listingItemId,
//				    li.quantity AS quantity,
//				    li.expiration_date AS itemExpirationDate,
//				    li.status AS itemStatus,
//				    i.item_id AS itemId,
//				    i.item_name AS itemName,
//				    i.category AS category,
//				    d.donor_id AS donorId,
//				    u.name AS donorName,
//				    u.email AS donorEmail,
//				    u.phone AS donorPhone,
//				    2 AS priority -- Regular listings have lower priority
//				FROM Listing l
//				JOIN ListingItem li ON l.listing_id = li.listing_id
//				JOIN Item i ON li.item_id = i.item_id
//				JOIN Donor d ON l.listed_by = d.donor_id
//				JOIN User u ON d.user_id = u.user_id
//				WHERE
//				    (? IS NULL OR i.category = ?)
//				    AND (? IS NULL OR li.quantity >= ?)
//				    AND (? IS NULL OR li.expiration_date <= ?)
//				    AND (? IS NULL OR l.pickup_time_range >= ?)
//				    AND (? IS NULL OR l.pickup_time_range <= ?)
//				    AND (? IS NULL OR l.location LIKE CONCAT('%', ?, '%'))
//				    AND (? IS NULL OR (
//				        ? IS NOT NULL AND
//				        ? IS NOT NULL AND
//				        ST_Distance_Sphere(POINT(l.longitude, l.latitude), POINT(?, ?)) <= ?))
//				    AND l.status = 'ACTIVE'
//				    AND li.status = 'AVAILABLE'
//				    AND (li.expiration_date IS NULL OR li.expiration_date > CURRENT_DATE)
//
//				ORDER BY 9,1;
//				        """;
//
//		Map<Long, Listing> listingsMap = new HashMap<>();
//
//		jdbcTemplate.query(sql, new Object[] { Integer.parseInt(recipientId), // For Recommendation query
//				foodType, foodType, quantityNeeded, quantityNeeded, expiryDate, expiryDate, pickupTimeStart,
//				pickupTimeStart, pickupTimeEnd, pickupTimeEnd, location, location, distance, userLatitude,
//				userLongitude, userLongitude, userLatitude, distance, foodType, foodType, quantityNeeded,
//				quantityNeeded, expiryDate, expiryDate, pickupTimeStart, pickupTimeStart, pickupTimeEnd, pickupTimeEnd,
//				location, location, distance, userLatitude, userLongitude, userLongitude, userLatitude, distance },
//				(rs) -> {
//					Long listingId = rs.getLong("listingId");
//					Listing listing = listingsMap.computeIfAbsent(listingId, id -> {
//						try {
//							Listing l = new Listing();
//							l.setListingId(id);
//							l.setLocation(rs.getString("location"));
//							l.setPickupTimeRange(rs.getString("pickupTimeRange"));
//							l.setStatus(rs.getString("listingStatus"));
//							l.setPriority(rs.getLong("priority"));
//
//							Donor donor = new Donor();
//							donor.setDonorId(rs.getLong("donorId"));
//							donor.setName(rs.getString("donorName"));
//							donor.setEmail(rs.getString("donorEmail"));
//							donor.setPhone(rs.getString("donorPhone"));
//
//							l.setDonor(donor);
//							l.setListingItems(new ArrayList<>());
//
//							return l;
//						} catch (SQLException e) {
//							throw new RuntimeException(e);
//						}
//					});
//
//					Long listingItemId = rs.getLong("listingItemId");
//					if (listingItemId != 0) {
//						ListingItem listingItem = new ListingItem();
//						listingItem.setListingItemId(listingItemId);
//						listingItem.setQuantity(rs.getLong("quantity"));
//						listingItem.setExpirationDate(rs.getDate("itemExpirationDate"));
//						listingItem.setStatus(rs.getString("itemStatus"));
//
//						Item itemDetails = new Item();
//						itemDetails.setItemId(rs.getLong("itemId"));
//						itemDetails.setItemName(rs.getString("itemName"));
//						itemDetails.setCategory(rs.getString("category"));
//
//						listingItem.setItem(itemDetails);
//						listing.getListingItems().add(listingItem);
//					}
//				});
//
//		return new ArrayList<>(listingsMap.values());
//	}
	
	@SuppressWarnings("deprecation")
	public List<Listing> findListingsWithFilters(String foodType, Long quantityNeeded, Date expiryDate,
			String pickupTimeStart, String pickupTimeEnd, String location, Double distance, Double userLongitude,
			Double userLatitude, String recipientId) {

		String procedureCall = "{CALL FindListingsWithFilters(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

		Map<Long, Listing> listingsMap = new HashMap<>();

		jdbcTemplate.query(procedureCall, new Object[] { Integer.parseInt(recipientId), 
				foodType, quantityNeeded, expiryDate, pickupTimeStart, pickupTimeEnd, location, distance, userLongitude,
				userLatitude }, (rs) -> {
					Long listingId = rs.getLong("listingId");
					Listing listing = listingsMap.computeIfAbsent(listingId, id -> {
						try {
							Listing l = new Listing();
							l.setListingId(id);
							l.setLocation(rs.getString("location"));
							l.setPickupTimeRange(rs.getString("pickupTimeRange"));
							l.setStatus(rs.getString("listingStatus"));
							l.setPriority(rs.getLong("priority"));

							Donor donor = new Donor();
							donor.setDonorId(rs.getLong("donorId"));
							donor.setName(rs.getString("donorName"));
							donor.setEmail(rs.getString("donorEmail"));
							donor.setPhone(rs.getString("donorPhone"));

							l.setDonor(donor);
							l.setListingItems(new ArrayList<>());

							return l;
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					});

					Long listingItemId = rs.getLong("listingItemId");
					if (listingItemId != 0) {
						ListingItem listingItem = new ListingItem();
						listingItem.setListingItemId(listingItemId);
						listingItem.setQuantity(rs.getLong("quantity"));
						listingItem.setExpirationDate(rs.getDate("itemExpirationDate"));
						listingItem.setStatus(rs.getString("itemStatus"));

						Item itemDetails = new Item();
						itemDetails.setItemId(rs.getLong("itemId"));
						itemDetails.setItemName(rs.getString("itemName"));
						itemDetails.setCategory(rs.getString("category"));

						listingItem.setItem(itemDetails);
						listing.getListingItems().add(listingItem);
					}
				});

		return new ArrayList<>(listingsMap.values());
	}

	public Long saveListing(CreateListingDTO listing) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

		return transactionTemplate.execute(status -> {
			try {
				String listingSql = "INSERT INTO Listing(listed_by, location, latitude, longitude, type, pickup_time_range, status) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

				KeyHolder keyHolder = new GeneratedKeyHolder();
				jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(listingSql, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, listing.getDonorId());
					ps.setString(2, listing.getLocation());
					if (listing.getLatitude() != null) {
						ps.setDouble(3, listing.getLatitude());
					} else {
						ps.setNull(3, Types.DOUBLE);
					}
					if (listing.getLongitude() != null) {
						ps.setDouble(4, listing.getLongitude());
					} else {
						ps.setNull(4, Types.DOUBLE);
					}
					ps.setString(5, listing.getType());
					ps.setString(6, listing.getPickupTimeRange());
					ps.setString(7, listing.getStatus());
					return ps;
				}, keyHolder);

				Long listingId = keyHolder.getKey().longValue();

				String listingItemSql = "INSERT INTO ListingItem (item_id, listing_id, quantity, expiration_date, status) "
						+ "VALUES (?, ?, ?, ?, ?)";

				for (CreateListingItemDTO item : listing.getListingItems()) {
					jdbcTemplate.update(listingItemSql, item.getItemId(), listingId, item.getQuantity(),
							new java.sql.Date(item.getExpirationDate().getTime()), item.getStatus());
				}

				return listingId;

			} catch (Exception e) {
				status.setRollbackOnly();
				log.error("Transaction to insert listing Failed : ", e);
				throw new RuntimeException("Failed to create listing", e);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public Listing findListingByListingId(Long listingId) {
		String sql = "SELECT l.*, u.name AS donor_name, d.preferred_pickup_time, d.type, d.donor_id, l.location as listing_location, u.user_id, u.email, u.phone, u.location as user_location, u.contact_preference, "
				+ "li.listing_item_id, li.quantity, li.expiration_date AS item_expiration_date, li.status AS item_status, li.booking_id, "
				+ "i.item_id, i.item_name, i.category " + "FROM Listing l "
				+ "LEFT JOIN Donor d ON l.listed_by = d.donor_id " + "LEFT JOIN User u ON d.user_id = u.user_id  "
				+ "LEFT JOIN ListingItem li ON l.listing_id = li.listing_id "
				+ "LEFT JOIN Item i ON li.item_id = i.item_id " + "WHERE l.listing_id = ?";

		log.info("Finding Listing Details Query for listing id {} : {}", listingId, sql);
		Map<Long, Listing> listingMap = new HashMap<>();

		jdbcTemplate.query(sql, new Object[] { listingId }, (rs) -> {
			Long currentListingId = rs.getLong("listing_id");
			Listing listing = listingMap.computeIfAbsent(currentListingId, k -> {
				try {
					Listing l = new Listing();
					l.setListingId(currentListingId);
					l.setLocation(rs.getString("listing_location"));
					l.setType(rs.getString("type"));
					l.setPickupTimeRange(rs.getString("pickup_time_range"));
					l.setStatus(rs.getString("status"));

					Donor donor = new Donor();
					donor.setDonorId(rs.getLong("donor_id"));
					donor.setPreferredPickupTime(rs.getString("preferred_pickup_time"));
					donor.setName(rs.getString("donor_name"));
					donor.setEmail(rs.getString("email"));
					donor.setPhone(rs.getString("phone"));
					donor.setLocation(rs.getString("user_location"));
					donor.setUserId(rs.getLong("user_id"));
					donor.setContactPreference(rs.getString("contact_preference"));
					donor.setType(rs.getString("type"));
					l.setDonor(donor);
					l.setListingItems(new ArrayList<>());
					return l;
				} catch (SQLException e) {
					log.error("Exception while ");
					throw new RuntimeException(e);
				}
			});

			Long listingItemId = rs.getLong("listing_item_id");
			if (listingItemId != 0) {
				ListingItem listingItem = new ListingItem();
				listingItem.setListingId(listingId);
				listingItem.setBookingId(rs.getLong("booking_id"));
				listingItem.setListingItemId(listingItemId);
				listingItem.setQuantity(rs.getLong("quantity"));
				listingItem.setExpirationDate(rs.getDate("item_expiration_date"));
				listingItem.setStatus(rs.getString("item_status"));

				Item itemDetails = new Item();
				itemDetails.setItemId(rs.getLong("item_id"));
				itemDetails.setItemName(rs.getString("item_name"));
				itemDetails.setCategory(rs.getString("category"));
				listingItem.setItem(itemDetails);
				listing.getListingItems().add(listingItem);
			}
		});

		return listingMap.values().iterator().next();
	}

	@SuppressWarnings("deprecation")
	public List<Listing> findAllListingsByDonor(Long donorId) {
		String sql = "SELECT l.*, u.name AS donor_name, d.preferred_pickup_time, d.type, d.donor_id, l.location as listing_location, u.user_id, u.email, u.phone, u.location as user_location, u.contact_preference, "
				+ "li.listing_item_id, li.quantity, li.expiration_date AS item_expiration_date, li.status AS item_status, li.booking_id, "
				+ "i.item_id, i.item_name, i.category " + "FROM Listing l "
				+ "LEFT JOIN Donor d ON l.listed_by = d.donor_id " + "LEFT JOIN User u ON l.listed_by = u.user_id "
				+ "LEFT JOIN ListingItem li ON l.listing_id = li.listing_id "
				+ "LEFT JOIN Item i ON li.item_id = i.item_id " + "WHERE l.listed_by = ? ORDER BY listing_id desc";

		log.info("Finding All Listings Query for donor id {} : {}", donorId, sql);
		Map<Long, Listing> listingMap = new HashMap<>();

		jdbcTemplate.query(sql, new Object[] { donorId }, (rs) -> {
			Long currentListingId = rs.getLong("listing_id");
			Listing listing = listingMap.computeIfAbsent(currentListingId, k -> {
				try {
					Listing l = new Listing();
					l.setListingId(currentListingId);
					l.setLocation(rs.getString("listing_location"));
					l.setType(rs.getString("type"));
					l.setPickupTimeRange(rs.getString("pickup_time_range"));
					l.setStatus(rs.getString("status"));

					Donor donor = new Donor();
					donor.setDonorId(rs.getLong("donor_id"));
					donor.setPreferredPickupTime(rs.getString("preferred_pickup_time"));
					donor.setName(rs.getString("donor_name"));
					donor.setEmail(rs.getString("email"));
					donor.setPhone(rs.getString("phone"));
					donor.setLocation(rs.getString("user_location"));
					donor.setUserId(rs.getLong("user_id"));
					donor.setContactPreference(rs.getString("contact_preference"));
					donor.setType(rs.getString("type"));
					l.setDonor(donor);
					l.setListingItems(new ArrayList<>());
					return l;
				} catch (SQLException e) {
					log.error("Exception while ");
					throw new RuntimeException(e);
				}
			});

			Long listingItemId = rs.getLong("listing_item_id");
			if (listingItemId != 0) {
				ListingItem listingItem = new ListingItem();
				listingItem.setListingId(rs.getLong("listing_id"));
				listingItem.setBookingId(rs.getLong("booking_id"));
				listingItem.setListingItemId(listingItemId);
				listingItem.setQuantity(rs.getLong("quantity"));
				listingItem.setExpirationDate(rs.getDate("item_expiration_date"));
				listingItem.setStatus(rs.getString("item_status"));

				Item itemDetails = new Item();
				itemDetails.setItemId(rs.getLong("item_id"));
				itemDetails.setItemName(rs.getString("item_name"));
				itemDetails.setCategory(rs.getString("category"));
				listingItem.setItem(itemDetails);
				listing.getListingItems().add(listingItem);
			}
		});

		return new ArrayList<>(listingMap.values());
	}

	public int updateListing(UpdateListingDTO updateListingDTO) {
		if (Objects.isNull(updateListingDTO.getListingId())) {
			throw new RuntimeException("Listing Id not provided");
		}

		StringBuilder sql = new StringBuilder("UPDATE Listing SET ");
		List<Object> parameters = new ArrayList<>();

		Map<String, Object> fieldsToUpdate = convertUpdatePOJOToMap(updateListingDTO);

		for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
			sql.append(entry.getKey()).append(" = ?, ");
			parameters.add(entry.getValue());
		}

		sql.setLength(sql.length() - 2);

		sql.append(" WHERE listing_id = ?");
		parameters.add(updateListingDTO.getListingId());

		return jdbcTemplate.update(sql.toString(), parameters.toArray());
	}

	private Map<String, Object> convertUpdatePOJOToMap(UpdateListingDTO updateListingDTO) {
		Map<String, Object> fieldsToUpdate = new HashMap<>();

		if (updateListingDTO.getLocation() != null) {
			fieldsToUpdate.put("location", updateListingDTO.getLocation());
		}
		if (updateListingDTO.getLatitude() != null) {
			fieldsToUpdate.put("latitude", updateListingDTO.getLatitude());
		}
		if (updateListingDTO.getLongitude() != null) {
			fieldsToUpdate.put("longitude", updateListingDTO.getLongitude());
		}
		if (updateListingDTO.getType() != null) {
			fieldsToUpdate.put("type", updateListingDTO.getType());
		}
		if (updateListingDTO.getPickupTimeRange() != null) {
			fieldsToUpdate.put("pickup_time_range", updateListingDTO.getPickupTimeRange());
		}
		if (updateListingDTO.getStatus() != null) {
			fieldsToUpdate.put("status", updateListingDTO.getStatus());
		}
		if (updateListingDTO.getExpirationDate() != null) {
			fieldsToUpdate.put("expiration_date", updateListingDTO.getExpirationDate());
		}

		return fieldsToUpdate;
	}

	public Long saveItem(Item item) {
		String itemSql = "INSERT INTO Item(item_name, category) " + "VALUES (?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(itemSql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, item.getItemName());
			ps.setString(2, item.getCategory());
			return ps;
		}, keyHolder);

		Long itemId = keyHolder.getKey().longValue();
		return itemId;
	}

	public List<Item> getItems(String searchQuery) {
		String sql = "SELECT * from Item ";

		if (Objects.nonNull(searchQuery) && !searchQuery.isEmpty()) {
			sql += "WHERE CONCAT(item_name, category) LIKE '%" + searchQuery + "%'";
		}
		log.info("Finding All Items query : {}", sql);

		List<Item> items = jdbcTemplate.query(sql, (rs, rowNum) -> {
			Item item = new Item();
			item.setItemId(rs.getLong("item_id"));
			item.setItemName(rs.getString("item_name"));
			item.setCategory(rs.getString("category"));
			return item;
		});

		return items;
	}

	public void deleteListing(Long listingId) {
		String deleteListingQuery = "DELETE FROM Listing where listing_id= " + listingId;
		String deleteListingItemsQuery = "DELETE FROM ListingItem where listing_id= " + listingId;
		jdbcTemplate.update(deleteListingItemsQuery);
		jdbcTemplate.update(deleteListingQuery);
	}
}
