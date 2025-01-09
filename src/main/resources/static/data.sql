CREATE OR REPLACE FUNCTION update_inventory_on_add()
RETURNS TRIGGER AS $$
BEGIN
UPDATE product
SET quantity = quantity + NEW.quantity
WHERE id = NEW.id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_inventory_add_trigger
    AFTER INSERT ON product
    FOR EACH ROW
    EXECUTE FUNCTION update_inventory_on_add();

CREATE OR REPLACE FUNCTION update_inventory_on_order()
RETURNS TRIGGER AS $$
BEGIN
UPDATE product
SET quantity = quantity - NEW.quantity
WHERE id = NEW.product_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_inventory_order_trigger
    AFTER INSERT ON orders
    FOR EACH ROW
    EXECUTE FUNCTION update_inventory_on_order();