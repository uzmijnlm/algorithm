package org.example.linkedlist;

public class Advanced {

    public static boolean isPalindrome(Node node) {
        if (node == null) {
            return false;
        }

        Node fast = node;
        Node slow = node;
        Node prevSlow = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            prevSlow = slow;
            slow = slow.next;
        }

        boolean isOdd = fast != null;
        if (isOdd) {
            Node mid = slow;
            slow = slow.next;
            mid.next = null;
            Node rightHalf = Basic.reverse(slow);
            Node right = rightHalf;
            Node left = node;
            while (right != null) {
                if (left.value != right.value) {
                    return false;
                }
                left = left.next;
                right = right.next;
            }
            mid.next = Basic.reverse(rightHalf);
        } else {
            prevSlow.next = null;
            Node rightHalf = Basic.reverse(slow);
            Node right = rightHalf;
            Node left = node;
            while (right != null) {
                if (left.value != right.value) {
                    return false;
                }
                left = left.next;
                right = right.next;
            }
            prevSlow.next = Basic.reverse(rightHalf);
        }
        return true;
    }

    public static Node splitThreeParts(Node node, int num) {
        if (node == null) {
            return null;
        }

        Node lowHead = null;
        Node lowTail = null;
        Node midHead = null;
        Node midTail = null;
        Node highHead = null;
        Node highTail = null;

        Node cur = node;
        Node next;
        while (cur != null) {
            next = cur.next;
            cur.next = null;

            if (cur.value < num) {
                if (lowHead == null) {
                    lowHead = cur;
                } else {
                    lowTail.next = cur;
                }
                lowTail = cur;
            } else if (cur.value == num) {
                if (midHead == null) {
                    midHead = cur;
                } else {
                    midTail.next = cur;
                }
                midTail = cur;
            } else {
                if (highHead == null) {
                    highHead = cur;
                } else {
                    highTail.next = cur;
                }
                highTail = cur;
            }

            cur = next;
        }

        if (lowTail != null) {
            if (midHead != null) {
                lowTail.next = midHead;
                if (highHead != null) {
                    midTail.next = highHead;
                }
            } else {
                if (highHead != null) {
                    lowTail.next = highHead;
                }
            }
        } else {
            if (midTail != null) {
                if (highHead != null) {
                    midTail.next = highHead;
                }
            }
        }

        if (lowHead != null) {
            return lowHead;
        } else {
            if (midHead != null) {
                return midHead;
            } else {
                return highHead;
            }
        }
    }

    public static Node findCrossPoint(Node node1, Node node2) {
        if (node1 == null || node2 == null) {
            return null;
        }

        boolean hasLoop1 = Basic.hasLoop(node1);
        boolean hasLoop2 = Basic.hasLoop(node2);

        if (!hasLoop1 && !hasLoop2) {
            return Basic.getCommonStart(node1, node2);
        } else if (hasLoop1 && hasLoop2) {
            Node entry1 = Basic.findEntryNode(node1);
            Node entry2 = Basic.findEntryNode(node2);
            if (entry1 == entry2) {
                return getCommonStartForSameEntry(node1, node2, entry1);
            } else {
                boolean separateLoop = true;
                Node cur = entry1;
                while (true) {
                    cur = cur.next;
                    if (cur == entry2) {
                        separateLoop = false;
                        break;
                    }
                    if (cur == entry1) {
                        break;
                    }
                }
                if (separateLoop) {
                    return null;
                } else {
                    return entry2;
                }
            }
        } else {
            return null;
        }
    }

    private static Node getCommonStartForSameEntry(Node node1, Node node2, Node entry) {
        if (node1 == null || node2 == null) {
            return null;
        }

        int size1 = 0;
        int size2 = 0;
        Node cur1 = node1;
        Node cur2 = node2;
        while (cur1 != entry) {
            size1++;
            cur1 = cur1.next;
        }
        while (cur2 != entry) {
            size2++;
            cur2 = cur2.next;
        }

        Node longer = size1 > size2 ? node1 : node2;
        Node shorter = size1 <= size2 ? node1 : node2;
        int diff = size1 > size2 ? size1 - size2 : size2 - size1;

        Node cur3 = longer;
        while (diff != 0) {
            diff--;
            cur3 = cur3.next;
        }
        Node cur4 = shorter;
        while (cur3 != cur4) {
            cur3 = cur3.next;
            cur4 = cur4.next;
            if (cur3 == cur4) {
                return cur3;
            }
        }
        return cur3;
    }

    public static Node selectSort(Node node) {
        Node head = null;
        Node tail = null;
        Node cur = node;

        while (cur != null) {
            Node preSmall = getPreSmall(cur);
            Node small;
            if (preSmall == null) {
                small = cur;
                cur = small.next;
            } else {
                small = preSmall.next;
                preSmall.next = small.next;
                small.next = null;
            }

            if (head == null) {
                head = small;
                tail = small;
            } else {
                tail.next = small;
                tail = small;
            }
        }

        return head;
    }

    private static Node getPreSmall(Node node) {
        Node cur = node;
        Node preSmall = null;
        Node small = node;
        Node pre = null;
        while (cur != null) {
            if (cur.value < small.value) {
                small = cur;
                preSmall = pre;
            }
            pre = cur;
            cur = cur.next;
        }
        return preSmall;
    }


    public static Node insertSort(Node node) {
        if (node == null) {
            return null;
        }

        Node preHead = new Node(-1);
        preHead.next = node;
        Node cur = node.next;
        Node tail = node;
        while (cur != null) {
            if (cur.value >= tail.value) {
                tail = cur;
                cur = cur.next;
            } else {
                Node pre = preHead;
                while (pre.next.value < cur.value) {
                    pre = pre.next;
                }
                tail.next = cur.next;
                cur.next = pre.next;
                pre.next = cur;
                cur = tail.next;
            }
        }
        return preHead.next;
    }


    public static Node mergeSort(Node node) {
        if (node == null) {
            return null;
        }

        int length = 0;
        Node cur = node;
        while (cur != null) {
            length++;
            cur = cur.next;
        }

        Node preHead = new Node(-1);
        preHead.next = node;
        for (int subLength = 1; subLength < length; subLength = subLength * 2) {
            Node pre = preHead;
            Node curNode = pre.next;
            while (curNode != null) {
                Node head1 = curNode;
                for (int i = 1; i < subLength && curNode.next != null; i++) {
                    curNode = curNode.next;
                }

                Node head2 = curNode.next;
                curNode.next = null;
                curNode = head2;
                for (int i = 1; i < subLength && curNode != null && curNode.next != null; i++) {
                    curNode = curNode.next;
                }

                Node next = null;
                if (curNode != null) {
                    next = curNode.next;
                    curNode.next = null;
                }

                pre.next = merge(head1, head2);
                while (pre.next != null) {
                    pre = pre.next;
                }

                curNode = next;
            }
        }
        return preHead.next;
    }

    private static Node merge(Node head1, Node head2) {
        Node preHead = new Node(-1);
        Node cur = preHead;
        Node cur1 = head1;
        Node cur2 = head2;
        while (cur1 != null && cur2 != null) {
            if (cur1.value < cur2.value) {
                cur.next = cur1;
                cur1 = cur1.next;
            } else {
                cur.next = cur2;
                cur2 = cur2.next;
            }
            cur = cur.next;
        }

        if (cur1 != null) {
            cur.next = cur1;
        }
        if (cur2 != null) {
            cur.next = cur2;
        }

        return preHead.next;
    }
}
