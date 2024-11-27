package ua.com.owu.crm_programming_school.specification;

import org.springframework.data.jpa.domain.Specification;
import ua.com.owu.crm_programming_school.models.Order;

import java.security.Principal;
import java.time.LocalDate;


public class OrderSpecifications {


    public static Specification<Order> filterByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Order> filterBySurname(String surname) {
        return (root, query, criteriaBuilder) -> {
            if (surname == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + surname.toLowerCase() + "%");
        };
    }

    public static Specification<Order> filterByEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("email"), "%" + email + "%");
        };
    }


    public static Specification<Order> filterByPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            if (phone == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
        };
    }


    public static Specification<Order> filterByAge(Integer age) {
        return (root, query, criteriaBuilder) -> {
            if (age == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("age"), age);
        };
    }


    public static Specification<Order> filterByCourse(String course) {
        return (root, query, criteriaBuilder) -> {
            if (course == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("course"), course);
        };
    }

    public static Specification<Order> filterByCourseFormat(String courseFormat) {
        return (root, query, criteriaBuilder) -> {
            if (courseFormat == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("courseFormat"), courseFormat);
        };
    }

    public static Specification<Order> filterByCourseType(String courseType) {
        return (root, query, criteriaBuilder) -> {
            if (courseType == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("courseType"), courseType);
        };
    }

    public static Specification<Order> filterBySum(Integer sum) {
        return (root, query, criteriaBuilder) -> {
            if (sum == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("sum"), sum);
        };
    }

    public static Specification<Order> filterByStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            if ("New".equals(status)) {
                return criteriaBuilder.or(
                        criteriaBuilder.isNull(root.get("status")),
                        criteriaBuilder.equal(root.get("status"), "New")
                );
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Order> filterByAlreadyPaid(Integer alreadyPaid) {
        return (root, query, criteriaBuilder) -> {
            if (alreadyPaid == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("alreadyPaid"), alreadyPaid);
        };
    }

    public static Specification<Order> filterByGroup(String groupName) {
        return (root, query, criteriaBuilder) -> {
            if (groupName == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.join("group").get("name"), groupName);
        };
    }

    public static Specification<Order> filterByManager(String managerName) {
        return (root, query, criteriaBuilder) -> {
            if (managerName == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.join("manager").get("name"), managerName);
        };
    }

    public static Specification<Order> filterByMy(Principal principal, boolean isMy) {
        return (root, query, criteriaBuilder) -> {
            if (isMy) {
                if (principal == null) {
                    return criteriaBuilder.conjunction();
                }
                String currentUsername = principal.getName();
                return criteriaBuilder.equal(root.join("manager").get("email"), currentUsername);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }


    public static Specification<Order> filterByStartDate(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("created"), startDate.atStartOfDay());
        };
    }

    public static Specification<Order> filterByEndDate(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (endDate == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("created"), endDate.atTime(23, 59, 59));
        };
    }
}


